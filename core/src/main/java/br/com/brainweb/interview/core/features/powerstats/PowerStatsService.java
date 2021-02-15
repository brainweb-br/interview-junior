package br.com.brainweb.interview.core.features.powerstats;

import br.com.brainweb.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public Long create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }

    public Long update(PowerStats powerStats) {
        return powerStatsRepository.update(powerStats);
    }

    public boolean delete(Long powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }
}
