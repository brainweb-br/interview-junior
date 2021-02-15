package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.mapper.HeroMapper;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
