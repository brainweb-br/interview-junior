package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.parser.CompareGuyParser;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import br.com.brainweb.interview.model.request.CompareGuyRequest;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.FindGuyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        final UUID powerStatsId = powerStatsService.create(new PowerStats(createHeroRequest));
        return heroRepository.create(new Hero(createHeroRequest, powerStatsId));
    }

    @Transactional
    public FindGuyParser findGuyByIdOrName(FindGuyRequest findGuyRequest, boolean byName) {
        return heroRepository.findGuyByIdOrName(findGuyRequest, byName);
    }

    @Transactional
    public UUID updateGuyById(FindGuyParser FindGuyParser) {
        return heroRepository.updateGuyById(FindGuyParser);
    }

    @Transactional
    public void deleteGuyById(FindGuyRequest findGuyRequest) {
        heroRepository.deleteGuyById(findGuyRequest);
    }

    @Transactional
    public CompareGuyParser compareGuysById(CompareGuyRequest compareGuyRequest) {
        return heroRepository.compareGuysById(compareGuyRequest);
    }
}
