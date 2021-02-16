package br.com.brainweb.interview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroDto {

    private Long id;
    private String name;
    private String race;
    private Long strength;
    private Long agility;
    private Long dexterity;
    private Long intelligence;
}
