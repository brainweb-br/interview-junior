package br.com.brainweb.interview.model.response;

import br.com.brainweb.interview.model.dto.HeroDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroCompareResponse {

    List<Long> ids = new ArrayList<>();
    List<String> herois = new ArrayList<>();
    private Long strength;
    private Long agility;
    private Long dexterity;
    private Long intelligence;

    public HeroCompareResponse(HeroDto first, HeroDto second){
        this.ids.add(first.getId());
        this.ids.add(second.getId());
        this.herois.add(first.getName());
        this.herois.add(second.getName());
        this.agility = first.getAgility() - second.getAgility();
        this.dexterity = first.getDexterity() - second.getDexterity();
        this.intelligence = first.getIntelligence() - second.getIntelligence();
    }
}
