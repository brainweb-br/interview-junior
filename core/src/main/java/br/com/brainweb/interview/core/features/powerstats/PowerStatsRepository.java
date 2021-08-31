package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
            " (strength, agility, dexterity, intelligence)" +
            " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";
    private static final String FIND_BY_ID = "SELECT * FROM power_stats where id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM power_stats where id = '";
    private static final String UPDATE_BY_ID = "UPDATE " +
            "	interview_service.power_stats " +
            "SET " +
            "	strength = ?, " +
            "	agility = ?, " +
            "	dexterity = ?, " +
            "	intelligence = ?, " +
            "	updated_at = now() " +
            "WHERE " +
            "	id = ? ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(CREATE_POWER_STATS_QUERY, new BeanPropertySqlParameterSource(powerStats), UUID.class);
    }

    public PowerStats findById(UUID id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
                (rs, rowNum) -> PowerStats
                        .builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .agility(rs.getInt("agility"))
                        .strength(rs.getInt("strength"))
                        .dexterity(rs.getInt("dexterity"))
                        .intelligence(rs.getInt("intelligence"))
                        .createdAtDate(new Date(rs.getTimestamp("created_at").getTime()))
                        .updatedAtDate(new Date(rs.getTimestamp("updated_at").getTime()))
                        .build()).get(0);
    }

    public void deleteById(UUID id) {
        jdbcTemplate.execute(DELETE_BY_ID + id + "'");
    }

    public void updateStats(PowerStats powerStats) {
        jdbcTemplate.update(UPDATE_BY_ID, powerStats.getStrength(), powerStats.getAgility(), powerStats.getDexterity(), powerStats.getIntelligence(), powerStats.getId());
    }
}
