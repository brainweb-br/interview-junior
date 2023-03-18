package br.com.brainweb.interview.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroCompareDTO {

	private UUID heroOneId;
	private UUID heroTwoId;
	private int strength;
	private int agility;
	private int dexterity;
	private int intelligence;
	
	
}
