package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
        " (strength, agility, dexterity, intelligence)" +
        " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private static final String UPDATE_POWER_STATS_QUERY = "UPDATE power_stats " +
        "SET strength = :strength, agility = :agility, " +
        "dexterity = :dexterity, intelligence = :intelligence, updated_at = now() " +
        "WHERE id = id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_POWER_STATS_QUERY,
            new BeanPropertySqlParameterSource(powerStats),
            UUID.class);
    }

    @Transactional
    public void updateStats(UUID power_stats_id, HeroDTO hero) {
        Map<String, Object> paramsStats = Map.of(
            "id", power_stats_id,
            "strength", hero.getPowerStats().getStrength(),
            "agility", hero.getPowerStats().getAgility(),
            "dexterity", hero.getPowerStats().getDexterity(),
            "intelligence", hero.getPowerStats().getIntelligence()
        );
        namedParameterJdbcTemplate.update(UPDATE_POWER_STATS_QUERY, paramsStats);
    }
}
