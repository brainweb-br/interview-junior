package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.mapper.HeroMapper;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroDto;
import br.com.brainweb.interview.model.request.UpdateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_HERO_ID_QUERY = "SELECT * FROM HERO h" +
            " INNER JOIN POWER_STATS p" +
            " ON h.power_stats_id = p.id" +
            " WHERE h.id = ?";

    private static final String FIND_HERO_NAME_QUERY = "SELECT * FROM HERO h" +
            " INNER JOIN POWER_STATS p" +
            " ON h.power_stats_id = p.id" +
            " WHERE h.name = ?";

    private static final String UPDATE_HERO_QUERY = "UPDATE HERO" +
            " SET NAME = :name," +
            " RACE = :race," +
            " ENABLED = :enabled," +
            " UPDATED_AT = :updatedAt" +
            " WHERE ID = :id RETURNING ID";

    private static final String DELETE_HERO_QUERY = "DELETE FROM HERO" +
            " WHERE ID = :id RETURNING true";

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Long create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
            CREATE_HERO_QUERY,
            params,
            Long.class);
    }

    HeroDto findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_HERO_ID_QUERY,
                    new Object[]{id},
                    new HeroMapper());
        }catch (EmptyResultDataAccessException ex){
            return null;
        }

    }

    public HeroDto findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_HERO_NAME_QUERY,
                    new Object[]{name},
                    new HeroMapper());
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public boolean delete(Long id) {
        final Map<String, Object> params = Map.of("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(DELETE_HERO_QUERY,
                    params,
                    boolean.class);
        }catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    public Long update(Long id, UpdateHeroRequest request) {
        final Map<String, Object> params = Map.of("name", request.getName(),
                "race", request.getRace(),
                "enabled", request.getEnable(),
                "updatedAt", LocalDateTime.now(),
                "id", id);

        try{
            Long heroId = namedParameterJdbcTemplate.queryForObject(
                    UPDATE_HERO_QUERY,
                    params,
                    Long.class);
            return heroId;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
