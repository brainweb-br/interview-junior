package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
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

    private static final String UPDATE_HERO_QUERY = "UPDATE hero " +
        "SET name = :name, race = :race, updated_at = now() " +
        "WHERE id = :id RETURNING power_stats_id";

    private static final String UPDATE_HERO_STATS_QUERY = "UPDATE power_stats " +
        "SET strength = :strength, agility = :agility, " +
        "dexterity = :dexterity, intelligence = :intelligence, updated_at = now() " +
        "WHERE id = id";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
            CREATE_HERO_QUERY,
            params,
            UUID.class);
    }

    public HeroDTO findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);

        return namedParameterJdbcTemplate.queryForObject(
            FIND_HERO_BY_ID_QUERY,
            params,
            new HeroRowMapper()
        );
    }

    public HeroDTO findByName(String name) {
        final Map<String, Object> params = Map.of("name", name);

        return namedParameterJdbcTemplate.queryForObject(
            FIND_HERO_BY_NAME_QUERY,
            params,
            new HeroRowMapper()
        );
    }

    public void updateHero(HeroDTO hero) {
         final Map<String, Object> paramsHero = Map.of(
            "id", hero.getId(),
            "name", hero.getName(),
            "race", hero.getRace().name(),
            "updated_at", Timestamp.from(Instant.now()),
            "strength", hero.getPowerStats().getStrength(),
            "agility", hero.getPowerStats().getAgility(),
            "dexterity", hero.getPowerStats().getDexterity(),
            "intelligence", hero.getPowerStats().getIntelligence()
        );
        UUID power_stats_id = namedParameterJdbcTemplate.queryForObject(UPDATE_HERO_QUERY, paramsHero, UUID.class);

        Map<String, Object> paramsStats = Map.of(
            "id", power_stats_id,
            "strength", hero.getPowerStats().getStrength(),
            "agility", hero.getPowerStats().getAgility(),
            "dexterity", hero.getPowerStats().getDexterity(),
            "intelligence", hero.getPowerStats().getIntelligence()
        );
        namedParameterJdbcTemplate.update(UPDATE_HERO_STATS_QUERY, paramsStats);
    }
}
