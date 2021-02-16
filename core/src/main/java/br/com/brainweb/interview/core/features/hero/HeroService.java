package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroCompareDTO;
import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.dto.HeroesDTO;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID powerStatId = powerStatsService.create(new PowerStats(createHeroRequest));
        return heroRepository.create(new Hero(createHeroRequest, powerStatId));
    }

    public HeroDTO findById(UUID heroId) {
        return heroRepository.findById(heroId);
    }

    public HeroDTO findByName(String name) {
        return heroRepository.findByName(name);
    }

    public void updateHero(UUID id, HeroDTO hero) {
        hero.setId(id);
        heroRepository.updateHero(hero);
    }

    public void delete(UUID heroId) {
        heroRepository.delete(heroId);
    }

    public HeroesDTO compare(UUID id1, UUID id2) {
        List<HeroCompareDTO> heroes = heroRepository.compare(id1, id2);

        if (heroes.isEmpty()) {
            return null;
        }

        HeroCompareDTO hero1 = heroes.get(0);
        HeroCompareDTO hero2 = heroes.get(1);

        return HeroesDTO.builder()
            .hero1Id(hero1.getId())
            .hero2Id(hero2.getId())
            .strengthDiff(hero1.getStrength() - hero2.getStrength())
            .agilityDiff(hero1.getAgility() - hero2.getAgility())
            .dexterityDiff(hero1.getDexterity() - hero2.getDexterity())
            .intelligenceDiff(hero1.getIntelligence() - hero2.getIntelligence())
            .build();
    }
}
