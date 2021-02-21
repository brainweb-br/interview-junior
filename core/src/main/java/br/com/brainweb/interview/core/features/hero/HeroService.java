package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.*;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepo;

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID powerStatsId = powerStatsRepository.create(new PowerStats(createHeroRequest));
        return heroRepo.create(new Hero(createHeroRequest, powerStatsId));
    }

    public HeroDTO findById(UUID uuid) {
        HeroDTO hero = heroRepo.findById(uuid);
        hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId()));
        return hero;
    }

    public List<HeroDTO> filterByName(String name) {
        List<HeroDTO> heroes = heroRepo.findByName(name);
        heroes.forEach(hero -> hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId())));
        return heroes;
    }

    public HeroDTO updateHeroById(UUID id, HeroDTO newHero) {
        HeroDTO savedHero = findById(id);
        savedHero = mapHero(savedHero, newHero);
        heroRepo.updateHero(savedHero);
        powerStatsRepository.updateStats(savedHero.getPowerStats());
        return savedHero;
    }

    public void deleteById(UUID uuid) {
        HeroDTO hero = heroRepo.findById(uuid);
        heroRepo.deleteById(uuid);
        powerStatsRepository.deleteById(hero.getPowerStats().getId());
    }

    private HeroDTO mapHero(HeroDTO savedHero, HeroDTO newHero) {
        savedHero.setName(newHero.getName());
        savedHero.setRace(newHero.getRace());
        savedHero.setEnabled(newHero.isEnabled());
        savedHero.setPowerStats(mapPowerStats(savedHero.getPowerStats(), newHero.getPowerStats()));
        return savedHero;
    }

    private PowerStats mapPowerStats(PowerStats savedPowerStats, PowerStats newPowerStats) {
        savedPowerStats.setStrength(newPowerStats.getStrength());
        savedPowerStats.setAgility(newPowerStats.getAgility());
        savedPowerStats.setDexterity(newPowerStats.getDexterity());
        savedPowerStats.setIntelligence(newPowerStats.getIntelligence());
        return savedPowerStats;
    }

    public List<ComparedHeroDTO> compareHeroes(UUID heroID1, UUID heroID2){
        ComparedHeroDTO comparedHero1 = new ComparedHeroDTO();
        PowerStats powerstats1 = findById(heroID1).getPowerStats();
        comparedHero1.setId(heroID1);

        ComparedHeroDTO comparedHero2 = new ComparedHeroDTO();
        PowerStats powerstats2 = findById(heroID2).getPowerStats();
        comparedHero2.setId(heroID2);

        int[] hero1Skills = {powerstats1.getStrength(), powerstats1.getAgility(), powerstats1.getDexterity(),powerstats1.getIntelligence()};

        powerstats1.setStrength(powerstats1.getStrength() - powerstats2.getStrength());
        powerstats1.setAgility(powerstats1.getAgility() - powerstats2.getAgility());
        powerstats1.setDexterity(powerstats1.getDexterity() - powerstats2.getDexterity());
        powerstats1.setIntelligence(powerstats1.getIntelligence() - powerstats2.getIntelligence());

        powerstats2.setStrength(powerstats2.getStrength() - hero1Skills[0]);
        powerstats2.setAgility(powerstats2.getAgility() - hero1Skills[1]);
        powerstats2.setDexterity(powerstats2.getDexterity() - hero1Skills[2]);
        powerstats2.setIntelligence(powerstats2.getIntelligence() - hero1Skills[3]);

        comparedHero1.setPowerStats(powerstats1);
        comparedHero2.setPowerStats(powerstats2);

        List<ComparedHeroDTO> comparedHeroList = new ArrayList<>();
        comparedHeroList.add(comparedHero1);
        comparedHeroList.add(comparedHero2);

        return comparedHeroList;
    }
}
