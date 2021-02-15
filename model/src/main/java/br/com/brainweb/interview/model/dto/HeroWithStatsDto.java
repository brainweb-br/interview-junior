package br.com.brainweb.interview.model.dto;

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
public class HeroWithStatsDto {

    private Long id;
    private String name;
    private String race;
    private Long powerStatsId;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private String createdAt;
    private String updatedAt;

}
