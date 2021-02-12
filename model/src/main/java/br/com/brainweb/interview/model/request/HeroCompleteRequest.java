package br.com.brainweb.interview.model.request;

import br.com.brainweb.interview.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroCompleteRequest {

    private Long id;
    private String name;
    private Race race;
    private Long pid;
    private boolean enabled;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

}
