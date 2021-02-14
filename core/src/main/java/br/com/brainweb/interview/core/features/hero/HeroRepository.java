package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_HERO_BY_ID_QUERY = "SELECT * FROM hero "+
        "INNER JOIN power_stats ON hero.power_stats_id = power_stats.id " +
        "WHERE hero.id = :id";

    private static final String FIND_HERO_BY_NAME_QUERY = "SELECT * FROM hero "+
        "INNER JOIN power_stats ON hero.power_stats_id = power_stats.id " +
        "WHERE hero.name LIKE :name";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
            CREATE_HERO_QUERY,
            params,
            UUID.class);
    }

    HeroDTO findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);

        return namedParameterJdbcTemplate.queryForObject(
            FIND_HERO_BY_ID_QUERY,
            params,
            new HeroRowMapper()
        );
    }

    HeroDTO findByName(String name) {
        final Map<String, Object> params = Map.of("name", name);

        return namedParameterJdbcTemplate.queryForObject(
            FIND_HERO_BY_NAME_QUERY,
            params,
            new HeroRowMapper()
        );
    }
}
