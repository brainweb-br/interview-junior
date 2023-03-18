package br.com.brainweb.interview.model;

import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.HeroCompleteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    private Long id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public PowerStats(CreateHeroRequest createHeroRequest) {
        this.strength = createHeroRequest.getStrength();
        this.agility = createHeroRequest.getAgility();
        this.dexterity = createHeroRequest.getDexterity();
        this.intelligence = createHeroRequest.getIntelligence();
    }

    public static PowerStats create(HeroCompleteRequest request) {
        PowerStats stats = new PowerStats();
        stats.setId(request.getPid());
        stats.setStrength(request.getStrength());
        stats.setAgility(request.getAgility());
        stats.setDexterity(request.getDexterity());
        stats.setIntelligence(request.getIntelligence());
        return stats;
    }
}
