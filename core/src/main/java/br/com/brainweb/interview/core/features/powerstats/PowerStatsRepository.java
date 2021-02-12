package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
        " (strength, agility, dexterity, intelligence)" +
        " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private static final String UPDATE_STATS = "UPDATE POWER_STATS " +
            "SET strength = :strength, " +
            "agility = :agility, " +
            "dexterity = :dexterity, " +
            "intelligence = :intelligence, " +
            "updated_at = :updatedAt " +
            "WHERE ID = :id RETURNING ID";

    private static final String DELETE =  "DELETE FROM POWER_STATS " +
            "WHERE POWER_STATS.ID = :id RETURNING true";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    Long create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_POWER_STATS_QUERY,
            new BeanPropertySqlParameterSource(powerStats),
            Long.class);
    }

    public Long update(PowerStats powerStats) {

        final Map<String, Object> paramsStats = Map.of("strength", powerStats.getStrength(),
                "agility", powerStats.getAgility(),
                "dexterity", powerStats.getDexterity(),
                "intelligence", powerStats.getIntelligence(),
                "updatedAt", Timestamp.from(Instant.now()),
                "id", powerStats.getId());

        Long statsId = namedParameterJdbcTemplate.queryForObject(
                UPDATE_STATS,
                paramsStats,
                Long.class);

        return statsId;

    }

    public boolean delete(Long powerStatsId) {
        final Map<String, Object> params = Map.of("id", powerStatsId);
        Boolean heroId = namedParameterJdbcTemplate.queryForObject(
                DELETE,
                params,
                Boolean.class);

        return heroId;
    }
}
