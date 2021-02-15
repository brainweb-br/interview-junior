package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroDto;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.HeroCompareRequest;
import br.com.brainweb.interview.model.request.UpdateHeroRequest;
import br.com.brainweb.interview.model.response.HeroCompareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public HeroDto findById(Long id) {
        return heroRepository.findById(id);
    }

    @Transactional
    public HeroDto findByName(String name) {
        return heroRepository.findByName(name);
    }

    public Boolean delete(Long id) {
        HeroDto dto = heroRepository.findById(id);

        return dto != null ? heroRepository.delete(id) : false;
    }

    public Long update(Long id, UpdateHeroRequest request) {
        HeroDto dto = heroRepository.findById(id);

        if (dto != null){
            return heroRepository.update(id, request);
        }
        return null;
    }

    public HeroCompareResponse compareHero(HeroCompareRequest request) {
        HeroDto firstHero = findByName(request.getFirstHero());
        HeroDto secondHero = findByName(request.getSecondHero());

        if (firstHero != null && secondHero != null){
            return new HeroCompareResponse(firstHero, secondHero);
        }
        return null;
    }
}
