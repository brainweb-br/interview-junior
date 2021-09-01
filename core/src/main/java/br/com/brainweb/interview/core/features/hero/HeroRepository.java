package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.organizer.FindGuyOrganizer;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.parser.CompareGuyParser;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import br.com.brainweb.interview.model.request.CompareGuyRequest;
import br.com.brainweb.interview.model.request.FindGuyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
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

    private static final String DELETE_GUY_BY_ID_QUERY = "DELETE from hero " +
            "WHERE id = :id RETURNING name";

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

    void deleteGuyById(FindGuyRequest findGuyRequest) {
        final Map<String, Object> params = Map.of("id", findGuyRequest.getId());

        namedParameterJdbcTemplate.queryForObject(
                DELETE_GUY_BY_ID_QUERY,
                params,
                String.class);
    }

    @Async
    CompareGuyParser compareGuysById(CompareGuyRequest compareGuyRequest) {
        final Map<String, Object> params1 = Map.of("id", compareGuyRequest.getId1());
        final Map<String, Object> params2 = Map.of("id", compareGuyRequest.getId2());

        FindGuyParser guy1 = namedParameterJdbcTemplate.queryForObject(
                FIND_GUY_BY_ID_QUERY,
                params1,
                new FindGuyOrganizer());

        FindGuyParser guy2 = namedParameterJdbcTemplate.queryForObject(
                FIND_GUY_BY_ID_QUERY,
                params2,
                new FindGuyOrganizer());

        return CompareGuyParser.builder().
                id1(guy1.getId()).name1(guy1.getName()).race1(guy1.getRace()).strength1(guy1.getStrength()).agility1(guy1.getAgility()).dexterity1(guy1.getDexterity()).intelligence1(guy1.getIntelligence()).
                id2(guy2.getId()).name2(guy2.getName()).race2(guy2.getRace()).strength2(guy2.getStrength()).agility2(guy2.getAgility()).dexterity2(guy2.getDexterity()).intelligence2(guy2.getIntelligence()).
                strengthD(guy1.getStrength() - guy2.getStrength()).agilityD(guy1.getAgility() - guy2.getAgility()).dexterityD(guy1.getDexterity() - guy2.getDexterity()).intelligenceD(guy1.getIntelligence() - guy2.getIntelligence()).build();
    }
}
