package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    @Autowired
    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public Long create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }
}
