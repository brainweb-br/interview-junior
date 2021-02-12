package br.com.brainweb.interview.model;

import br.com.brainweb.interview.model.enums.Race;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class Hero {

    private Long id;
    private String name;
    private Race race;
    private Long powerStatsId;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;

    public Hero(CreateHeroRequest createHeroRequest, Long powerStatsId) {
        this.name = createHeroRequest.getName();
        this.race = createHeroRequest.getRace();
        this.powerStatsId = powerStatsId;
    }

    public static Hero create(HeroCompleteRequest request) {
        Hero hero = new Hero();
        hero.setId(request.getId());
        hero.setName(request.getName());
        hero.setRace(request.getRace());
        hero.setEnabled(request.isEnabled());
        return hero;
    }
}
