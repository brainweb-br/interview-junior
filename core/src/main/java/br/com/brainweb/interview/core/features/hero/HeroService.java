package br.com.brainweb.interview.core.features.hero;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeroService {

	private final HeroRepository heroRepository;

	private final PowerStatsRepository powerStatsRepository;

	@Transactional
	public UUID create(CreateHeroRequest createHeroRequest) {
		UUID uuidPowerStats = powerStatsRepository.create(new PowerStats(createHeroRequest));
		return heroRepository.create(new Hero(createHeroRequest, uuidPowerStats));
	}

	@Transactional
	public Hero getHeroById(UUID id) {
		Hero hero = null;
		try {
			hero = heroRepository.findHeroById(id);
		} catch (Exception e) {
			throw new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			};
		}
		return hero;
	}

	@Transactional
	public List<Hero> getHerosByName(String name) {
		List<Hero> heros = null;
		try {
			heros = heroRepository.findHerosByName(name);
		} catch (Exception e) {
			throw new HttpStatusCodeException(HttpStatus.OK) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			};
		}
		return heros;
	}

	@Transactional
	public Integer updateHero(Hero heroUpdated) {
		Hero hero = null;
		try {
			hero = heroRepository.findHeroById(heroUpdated.getId());
			if (hero.getId() != null) {
				heroRepository.updateHero(heroUpdated);
			}
		} catch (Exception e) {
			throw new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			};
		}
		return 1;
	}

	@Transactional
	public Integer deleteHero(UUID id) {
		Hero hero = null;
		try {
			hero = heroRepository.findHeroById(id);
			if (hero.getId() != null) {
				heroRepository.deleteHero(hero);
			}
		} catch (Exception e) {
			throw new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			};
		}
		return 1;
	}
}
