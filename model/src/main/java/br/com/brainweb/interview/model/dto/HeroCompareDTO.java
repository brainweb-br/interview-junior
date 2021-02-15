package br.com.brainweb.interview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroCompareDTO {
    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
