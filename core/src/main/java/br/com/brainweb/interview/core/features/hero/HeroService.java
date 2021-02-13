package br.com.brainweb.interview.core.features.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.simple.JSONObject;
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

    @SuppressWarnings("unchecked")
    @Transactional
    public JSONObject getComparationsBetweenHeros(UUID id1, UUID id2) {
	List<Hero> heros = new ArrayList<>();
	JSONObject comparations = new JSONObject();
	JSONObject comparation = new JSONObject();
	JSONObject comparation1 = new JSONObject();
	List<JSONObject> listComparation = new ArrayList<>();
	try {
	    heros.add(heroRepository.findHeroById(id1));
	    heros.add(heroRepository.findHeroById(id2));

	    if (heros.size() == 2) {
		PowerStats powerStats1 = powerStatsRepository.findPowerStatsById(heros.get(0).getPowerStatsId());
		PowerStats powerStats2 = powerStatsRepository.findPowerStatsById(heros.get(1).getPowerStatsId());

		comparation.put("idObjectPrincipal", heros.get(0).getId());
		comparation.put("idObjectCompared", heros.get(1).getId());
		comparation.put("strength", powerStats1.getStrength() - powerStats2.getStrength());
		comparation.put("agility", powerStats1.getAgility() - powerStats2.getAgility());
		comparation.put("dexterity", powerStats1.getDexterity() - powerStats2.getDexterity());
		comparation.put("intelligence", powerStats1.getIntelligence() - powerStats2.getIntelligence());

		comparation1.put("idObjectPrincipal", heros.get(1).getId());
		comparation1.put("idObjectCompared", heros.get(0).getId());
		comparation1.put("strength", powerStats2.getStrength() - powerStats1.getStrength());
		comparation1.put("agility", powerStats2.getAgility() - powerStats1.getAgility());
		comparation1.put("dexterity", powerStats2.getDexterity() - powerStats1.getDexterity());
		comparation1.put("intelligence", powerStats2.getIntelligence() - powerStats1.getIntelligence());

		listComparation.add(comparation);
		listComparation.add(comparation1);
		comparations.put("comparations", listComparation);

	    }

	} catch (Exception e) {
	    throw new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	    };
	}
	return comparations;
    }
}
