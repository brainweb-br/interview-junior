package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.exception.GenericException;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.enums.Race;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " +
            " interview_service.hero WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM " +
            " interview_service.hero WHERE name like ?";
    private static final String DELETE_BY_ID = "DELETE FROM interview_service.hero where id = '";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
                CREATE_HERO_QUERY,
                params,
                UUID.class);
    }

    public HeroDTO findById(UUID uuid) {
        List<HeroDTO> heroes = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[]{uuid},
                (rs, rowNum) ->
                        HeroDTO
                                .builder()
                                .id(UUID.fromString(rs.getString("id")))
                                .name(rs.getString("name"))
                                .race(Race.valueOf(rs.getString("race")))
                                .powerStats(PowerStats.builder().id(UUID.fromString(rs.getString("power_stats_id"))).build())
                                .createdAtDate(new Date(rs.getTimestamp("created_at").getTime()))
                                .updatedAtDate(new Date(rs.getTimestamp("updated_at").getTime()))
                                .build()
        );
        if (heroes.isEmpty())
            throw new GenericException(HttpStatus.NOT_FOUND, "No hero with this id!");
        return heroes.get(0);
    }

    public void deleteById(UUID id) {
        if(Objects.isNull(findById(id)))
            throw new GenericException(HttpStatus.NOT_FOUND, "No hero with this id!");
        jdbcTemplate.execute(DELETE_BY_ID + id + '\'');
    }

    public List<HeroDTO> findByName(String name) {
        List<HeroDTO> heroes = jdbcTemplate.query(FIND_BY_NAME, new Object[]{"%" + name + "%"},
                (rs, rowNum) -> HeroDTO
                        .builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .name(rs.getString("name"))
                        .race(Race.valueOf(rs.getString("race")))
                        .powerStats(PowerStats.builder().id(UUID.fromString(rs.getString("power_stats_id"))).build())
                        .createdAtDate(new Date(rs.getTimestamp("created_at").getTime()))
                        .updatedAtDate(new Date(rs.getTimestamp("updated_at").getTime()))
                        .build()
        );
        return heroes;
    }

    public void updateHero(HeroDTO savedHero) {
        if(Objects.isNull(findById(savedHero.getId())))
            throw new GenericException(HttpStatus.NOT_FOUND, "No hero with this id!");
        jdbcTemplate.update(getUpdateQuery(savedHero.isEnabled()), savedHero.getName(), savedHero.getRace().toString(), savedHero.getId());
    }

    private String getUpdateQuery(boolean enabled) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append("	interview_service.hero ");
        sql.append("SET ");
        sql.append("	name = ?, ");
        sql.append("	race = ?, ");
        sql.append("	enabled = " + (enabled ? "TRUE" : "FALSE") + ", ");
        sql.append("	updated_at = now() ");
        sql.append("WHERE ");
        sql.append("	id = ? ");
        return sql.toString();
    }

}

