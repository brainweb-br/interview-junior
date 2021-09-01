package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }

    @Transactional
    public UUID updateGuyById(FindGuyParser FindGuyParser, UUID id) {
        return powerStatsRepository.updateGuyById(FindGuyParser, id);
    }
}
