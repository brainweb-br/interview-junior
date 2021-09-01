package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.organizer.FindGuyOrganizer;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import br.com.brainweb.interview.model.request.FindGuyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_GUY_BY_ID_QUERY = "SELECT * FROM hero " +
            "INNER JOIN power_stats ON hero.power_stats_id = power_stats.id " +
            "WHERE hero.id = :id";

    private static final String FIND_GUY_BY_NAME_QUERY = "SELECT * FROM hero " +
            "INNER JOIN power_stats ON hero.power_stats_id = power_stats.id " +
            "WHERE hero.name = :name";

    private static final String UPDATE_GUY_BY_ID_QUERY = "UPDATE hero " +
            "SET name = :name, " +
            "race = :race, " +
            "updated_at = :updated_at " +
            "WHERE id = :id RETURNING power_stats_id";

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

    FindGuyParser findGuyByIdOrName(FindGuyRequest findGuyRequest, boolean byName) {
        final Map<String, Object> params = byName ? Map.of("name", findGuyRequest.getName()) : Map.of("id", findGuyRequest.getId());

        return byName ?
                namedParameterJdbcTemplate.queryForObject(
                        FIND_GUY_BY_NAME_QUERY,
                        params,
                        new FindGuyOrganizer()) :
                namedParameterJdbcTemplate.queryForObject(
                        FIND_GUY_BY_ID_QUERY,
                        params,
                        new FindGuyOrganizer());
    }

    UUID updateGuyById(FindGuyParser findGuyParser) {
        final Map<String, Object> params = Map.of("name", findGuyParser.getName(),
                "race", findGuyParser.getRace().name(),
                "updated_at", Timestamp.from(Instant.now()),
                "id", findGuyParser.getId());

        return namedParameterJdbcTemplate.queryForObject(
                UPDATE_GUY_BY_ID_QUERY,
                params,
                UUID.class);
    }
}
