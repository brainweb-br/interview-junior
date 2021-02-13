package br.com.brainweb.interview.core.features.powerstats;

import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import br.com.brainweb.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats"
	    + " (strength, agility, dexterity, intelligence)"
	    + " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private static final String FIND_POWER_STATS_ID_QUERY = "SELECT * FROM power_stats WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UUID create(PowerStats powerStats) {
	return namedParameterJdbcTemplate.queryForObject(CREATE_POWER_STATS_QUERY,
		new BeanPropertySqlParameterSource(powerStats), UUID.class);
    }

    public PowerStats findPowerStatsById(UUID id) {
	final Map<String, Object> params = Map.of("id", id);
	return (PowerStats) namedParameterJdbcTemplate.queryForObject(FIND_POWER_STATS_ID_QUERY, params,
		new BeanPropertyRowMapper<PowerStats>(PowerStats.class));
    };
}
