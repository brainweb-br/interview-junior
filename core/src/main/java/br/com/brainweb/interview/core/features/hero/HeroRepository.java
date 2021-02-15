package br.com.brainweb.interview.core.features.hero;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import br.com.brainweb.interview.core.mapper.HeroMapper;
import br.com.brainweb.interview.model.dto.HeroWithStatsDto;
import br.com.brainweb.interview.model.request.HeroCompleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.model.Hero;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_HERO_BY_ID = "SELECT * FROM HERO H " +
            "INNER JOIN POWER_STATS P " +
            "ON H.POWER_STATS_ID = P.ID " +
            "WHERE H.ID = ?";

    private static final String FIND_HERO_BY_NAME = "SELECT * FROM HERO H " +
            "INNER JOIN POWER_STATS P " +
            "ON H.POWER_STATS_ID = P.ID " +
            "WHERE H.NAME LIKE ?";

    private static final String UPDATE_HERO = "UPDATE HERO " +
            "SET NAME = :name, " +
            "RACE = :race, " +
            "ENABLED = :enabled, " +
            "UPDATED_AT = :updatedAt " +
            "WHERE ID = :id RETURNING ID";

    private static final String DELETE =  "DELETE FROM HERO " +
            "WHERE HERO.ID = :id RETURNING true";

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

    HeroWithStatsDto findById(Long id){
        try {
            return jdbcTemplate.queryForObject(FIND_HERO_BY_ID, new Object[]{id}, new HeroMapper());

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    HeroWithStatsDto findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_HERO_BY_NAME, new Object[]{name}, new HeroMapper());

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    Long update(Hero hero) {
        final Map<String, Object> paramsHero = Map.of("name", hero.getName(),
                "race", hero.getRace().name(),
                "enabled", hero.isEnabled(),
                "updatedAt", Timestamp.from(Instant.now()),
                "id", hero.getId());

        try{
            Long heroId = namedParameterJdbcTemplate.queryForObject(
                    UPDATE_HERO,
                    paramsHero,
                    Long.class);
            return heroId;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public boolean delete(Long id) {
        try {
            final Map<String, Object> params = Map.of("id", id);
            Boolean heroId = namedParameterJdbcTemplate.queryForObject(
                    DELETE,
                    params,
                    Boolean.class);

            return heroId;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
