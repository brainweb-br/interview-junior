package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroWithStatsDto;
import br.com.brainweb.interview.model.dto.HeroesCompareDto;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.HeroCompleteRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

	@Autowired
    private final HeroRepository heroRepository;
    @Autowired
    private final PowerStatsService powerStatsService;

    @Transactional
    public Long create(CreateHeroRequest createHeroRequest) {
        PowerStats powerStats = new PowerStats(createHeroRequest);
        Long powerStatsId = powerStatsService.create(powerStats);
        return heroRepository.create(new Hero(createHeroRequest, powerStatsId));
    }

    @Transactional
    public HeroWithStatsDto findById(Long id){
        return heroRepository.findById(id);
    }

    public HeroWithStatsDto findByName(String name) {
        return heroRepository.findByName(name);
    }

    @Transactional
    public Long update(HeroCompleteRequest heroRequest) {
        Long statsId = powerStatsService.update(PowerStats.create(heroRequest));

        return heroRepository.update(Hero.create(heroRequest));
    }

    @Transactional
    public boolean delete(Long id) {
        HeroWithStatsDto hero = heroRepository.findById(id);
        if(hero == null){
            return false;
        }else{
            heroRepository.delete(id);
            return powerStatsService.delete(hero.getPowerStatsId());
        }
    }

    public HeroesCompareDto compare(Long firstHeroId, Long secondHeroId) {
        try {
            HeroesCompareDto response = HeroesCompareDto.create(heroRepository.findById(firstHeroId), heroRepository.findById(secondHeroId));
            return response;
        }catch (NullPointerException e){
            return null;
        }
    }
}
