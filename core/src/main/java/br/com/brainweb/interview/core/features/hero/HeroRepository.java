package br.com.brainweb.interview.core.features.hero;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.model.Hero;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

	private static final String CREATE_HERO_QUERY = "INSERT INTO hero" + " (name, race, power_stats_id)" + " VALUES (:name, :race, :powerStatsId) RETURNING id";

	private static final String FIND_HERO_ID_QUERY = "SELECT * FROM hero WHERE id = :id";

	private static final String FIND_HERO_NAME_QUERY = "SELECT * FROM hero WHERE name = :name";

	private static final String UPDATE_HERO_QUERY = "UPDATE hero SET name = :name, race = :race, power_stats_id = :powerStatsId, updated_at = Now() WHERE id = :id";

	private static final String DELETE_HERO_QUERY = "DELETE FROM hero WHERE id = :id";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	UUID create(Hero hero) {
		final Map<String, Object> params = Map.of("name", hero.getName(), "race", hero.getRace().name(), "powerStatsId", hero.getPowerStatsId());

		return namedParameterJdbcTemplate.queryForObject(CREATE_HERO_QUERY, params, UUID.class);
	}

	Hero findHeroById(UUID id) {
		final Map<String, Object> params = Map.of("id", id);
		return (Hero) namedParameterJdbcTemplate.queryForObject(FIND_HERO_ID_QUERY, params, new BeanPropertyRowMapper<Hero>(Hero.class));
	};

	List<Hero> findHerosByName(String name) {
		final Map<String, Object> params = Map.of("name", name);
		return namedParameterJdbcTemplate.query(FIND_HERO_NAME_QUERY, params, new BeanPropertyRowMapper<Hero>(Hero.class));
	};

	int updateHero(Hero hero) {
		final Map<String, Object> params = Map.of("id", hero.getId(), "name", hero.getName(), "race", hero.getRace().name(), "powerStatsId", hero.getPowerStatsId());
		return namedParameterJdbcTemplate.update(UPDATE_HERO_QUERY, params);
	};

	int deleteHero(Hero hero) {
		final Map<String, Object> params = Map.of("id", hero.getId());
		return namedParameterJdbcTemplate.update(DELETE_HERO_QUERY, params);
	};
}
