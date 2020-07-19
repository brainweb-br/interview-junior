package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_HERO_BY_ID = "SELECT " +
            "h.id, h.name, h.race, h.power_stats_id, h.enabled, h.created_at, h.updated_at " +
            //"p.id, p.strength, p.agility, p.dexterity, p.intelligence, p.created_at, p.updated_at" +
            "FROM hero h " +
            "WHERE h.id =:id ";

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

    Hero findById(UUID heroId) {
        final Map<String, Object> params = Map.of("id", heroId);

        return this.namedParameterJdbcTemplate.queryForObject(
                FIND_HERO_BY_ID,
                params,
                new HeroRowMapper()
        );
    }
}
