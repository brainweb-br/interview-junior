package br.com.brainweb.interview.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroDTO {
	
	 private UUID id;
	 private String name;
	 private String race;
	 private UUID powerStats;
	 private boolean enabled;
	 private Date created_at;
	 private Date updated_at;
	 
	 private int strength;
	 private int agility;
	 private int dexterity;
	 private int intelligence;
	 private Date created_atStats;
	 private Date updated_atStats;
	
}
