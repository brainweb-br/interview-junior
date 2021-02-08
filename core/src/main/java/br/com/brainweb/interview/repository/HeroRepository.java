package br.com.brainweb.interview.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.dto.HeroDTO;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.race.Race;


@Repository
@Transactional
public interface HeroRepository extends JpaRepository<Hero, UUID>{

	public Hero findByName(String name);
	
	public void deleteByName(String name);
	
	public List<Hero> findByRace(Race race);
	
	public List<Hero> findByPowerStats(UUID powerStats);
	
	@Query(value = "select new br.com.brainweb.interview.dto.HeroDTO"
			+ "(h.id,h.name, h.race.toString ,h.powerStats, h.enabled, h.created_at,h.updated_at,"
			+ "p.strength, p.agility,p.dexterity,p.intelligence, p.created_atStats, p.updated_atStats)"
			+ "FROM Hero h "
			+ "JOIN PowerStats p on h.powerStats = p.id"
			+ "where h.id = :id", nativeQuery = true) 		
	public HeroDTO findHeroToCompare(UUID idOne);
	
}
