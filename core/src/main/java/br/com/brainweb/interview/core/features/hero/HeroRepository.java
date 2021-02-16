package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroCompareDTO;
import br.com.brainweb.interview.model.dto.HeroDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String DELETE_HERO_QUERY = "DELETE FROM hero " +
        "WHERE hero.id = :id RETURNING id";

    private static final String COMPARE_HEROES_QUERY = "SELECT power_stats.id, power_stats.strength, " +
        "power_stats.agility, power_stats.dexterity, power_stats.intelligence FROM hero "+
        "INNER JOIN power_stats ON hero.power_stats_id = power_stats.id " +
        "WHERE hero.id IN (:id1, :id2)";


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

    @Transactional
    public UUID updateHero(HeroDTO hero) {
         final Map<String, Object> paramsHero = Map.of(
            "id", hero.getId(),
            "name", hero.getName(),
            "race", hero.getRace().name(),
            "updated_at", Timestamp.from(Instant.now())
        );

        return namedParameterJdbcTemplate.queryForObject(UPDATE_HERO_QUERY, paramsHero, UUID.class);
    }

    @Transactional
    public void delete(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcTemplate.queryForObject(DELETE_HERO_QUERY, params, UUID.class);
    }

    public List<HeroCompareDTO> compare(UUID id1, UUID id2) {
        final Map<String, Object> params = Map.of("id1", id1, "id2", id2);

        return namedParameterJdbcTemplate.query(
            COMPARE_HEROES_QUERY, params, new HeroCompareRowMapper()
        );
    }
}
