package br.com.brainweb.interview.model.dto;

import br.com.brainweb.interview.model.request.HeroesCompareRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroesCompareDto {

    private String firstHeroName;
    private String secondHeroName;
    private int strengthDiff;
    private int agilityDiff;
    private int dexterityDiff;
    private int intelligenceDiff;

    public static HeroesCompareDto create(HeroWithStatsDto firstHero, HeroWithStatsDto secondHero){
        HeroesCompareDto dto = new HeroesCompareDto();
        dto.setFirstHeroName(firstHero.getName());
        dto.setSecondHeroName(secondHero.getName());
        dto.setStrengthDiff(firstHero.getStrength() - secondHero.getStrength());
        dto.setAgilityDiff(firstHero.getAgility() - secondHero.getAgility());
        dto.setDexterityDiff(firstHero.getDexterity() - secondHero.getDexterity());
        dto.setIntelligenceDiff(firstHero.getIntelligence() - secondHero.getIntelligence());
        return dto;
    }

}
