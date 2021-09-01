package br.com.brainweb.interview.model.parser;

import br.com.brainweb.interview.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareGuyParser {
    private UUID id1;
    private String name1;
    private Race race1;
    private int strength1;
    private int agility1;
    private int dexterity1;
    private int intelligence1;

    private UUID id2;
    private String name2;
    private Race race2;
    private int strength2;
    private int agility2;
    private int dexterity2;
    private int intelligence2;

    private int strengthD;
    private int agilityD;
    private int dexterityD;
    private int intelligenceD;
}
