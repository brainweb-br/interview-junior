package br.com.brainweb.interview.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import br.com.brainweb.interview.race.Race;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hero {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private UUID id;
	 private UUID powerStats;
	 private String name;
	 private Race race;
	 private boolean enabled;
	 private Date created_at;
	 private Date updated_at;
	

	 
}
