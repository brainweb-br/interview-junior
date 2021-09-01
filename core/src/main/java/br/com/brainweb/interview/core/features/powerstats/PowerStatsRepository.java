package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.parser.FindGuyParser;
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

    private static final String UPDATE_GUY_BY_ID_QUERY = "UPDATE power_stats " +
            "SET strength = :strength, " +
            "agility = :agility, " +
            "dexterity = :dexterity, " +
            "intelligence = :intelligence, " +
            "updated_at = :updated_at " +
            "WHERE id = :id RETURNING id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_POWER_STATS_QUERY,
                new BeanPropertySqlParameterSource(powerStats),
                UUID.class);
    }

    UUID updateGuyById(FindGuyParser findGuyParser, UUID id) {
        final Map<String, Object> params = Map.of("strength", findGuyParser.getStrength(),
                "agility", findGuyParser.getAgility(),
                "dexterity", findGuyParser.getDexterity(),
                "intelligence", findGuyParser.getIntelligence(),
                "updated_at", Timestamp.from(Instant.now()),
                "id", id);

        return namedParameterJdbcTemplate.queryForObject(
                UPDATE_GUY_BY_ID_QUERY,
                params,
                UUID.class);
    }
}
