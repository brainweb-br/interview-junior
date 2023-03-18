package br.com.brainweb.interview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brainweb.interview.dto.HeroCompareDTO;
import br.com.brainweb.interview.dto.HeroDTO;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.race.Race;
import br.com.brainweb.interview.repository.HeroRepository;
import br.com.brainweb.interview.repository.PowerStatsRepo;



@Service
public class HeroService {

	@Autowired
	HeroRepository herorepo;
	
	@Autowired
	PowerStatsRepo powersrepo;
	
	public HeroDTO findHeroById(UUID id) {
		HeroDTO dto = new HeroDTO();
		Hero model = new Hero();
		
		model = herorepo.findById(id).get();
		
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPowerStats(model.getPowerStats());
		dto.setRace(model.getRace().toString());
		dto.setEnabled(model.isEnabled());
		dto.setCreated_at(model.getCreated_at());
		dto.setUpdated_at(model.getUpdated_at());		
		
		return dto;
	}
	
	public HeroDTO findHeroByName (String name) {			
			try {			
				HeroDTO dto = new HeroDTO();
				Hero model = new Hero();
				model = herorepo.findByName(name);
				
				dto.setId(model.getId());
				dto.setName(model.getName());
				dto.setPowerStats(model.getPowerStats());
				dto.setRace(model.getRace().toString());
				dto.setEnabled(model.isEnabled());
				dto.setCreated_at(model.getCreated_at());
				dto.setUpdated_at(model.getUpdated_at());
											
				return dto;
			} catch (Exception e) {
				throw e;
			}				
	}
	
	public List<HeroDTO> findAllHeroes() throws Exception{
		try {			
			List<HeroDTO> dto = new ArrayList();
			List<Hero> model = new ArrayList();
			
			model = herorepo.findAll();
			
			for(Hero h : model) {
				
				HeroDTO heroDTO = new HeroDTO();
				
				heroDTO.setId(h.getId());
				heroDTO.setName(h.getName());
				heroDTO.setPowerStats(h.getPowerStats());;
				heroDTO.setRace(h.getRace().toString());
				heroDTO.setEnabled(h.isEnabled());
				heroDTO.setCreated_at(h.getCreated_at());
				heroDTO.setUpdated_at(h.getUpdated_at());
				
				dto.add(heroDTO);
			}			
			return dto;
		}
		catch(Exception e) {
			throw e;
		}		
	}
	
	public List<HeroDTO> findHeroByRace(String raceString){
		try {			
			Race race = Race.valueOf(raceString);
			
			List<HeroDTO> dto = new ArrayList();
			List<Hero> model = new ArrayList();
			
			model = herorepo.findByRace(race);
			
			for(Hero h : model) {
				HeroDTO heroDTO = new HeroDTO();
				
				heroDTO.setId(h.getId());
				heroDTO.setName(h.getName());
				heroDTO.setPowerStats(h.getPowerStats());
				heroDTO.setRace(h.getRace().toString());
				heroDTO.setEnabled(h.isEnabled());
				heroDTO.setCreated_at(h.getCreated_at());
				heroDTO.setUpdated_at(h.getUpdated_at());
				
				dto.add(heroDTO);				
			}
			return dto;
		}
		catch(Exception e) {
			throw e;
		}				
	}
	
	public void saveHero(HeroDTO dto) {
		Hero model = new Hero();
		PowerStats modelStats = new PowerStats();
		
		try {
			
			modelStats.setAgility(dto.getAgility());
			modelStats.setCreated_at(dto.getCreated_atStats());
			modelStats.setIntelligence(dto.getIntelligence());
			modelStats.setDexterity(dto.getDexterity());
			modelStats.setStrength(dto.getStrength());
			modelStats.setUpdated_at(dto.getUpdated_atStats());
			
			powersrepo.save(modelStats);
			
			UUID lastStats = powersrepo.findMaxPowerStats();
			
			model.setName(dto.getName());
			model.setPowerStats(lastStats);
			model.setRace(Race.valueOf(dto.getRace()));
			model.setEnabled(dto.isEnabled());
			model.setCreated_at(dto.getCreated_at());
			model.setUpdated_at(dto.getUpdated_at());
			
			herorepo.save(model);			
		} catch (Exception e) {
		      throw e;
		}
		
	}
	
	public void deleteHeroByName(String name) {
		try {			
			herorepo.deleteByName(name);			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteHeroByID(UUID id) {
		try {			
			herorepo.deleteById(id);			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteAllHeroes(){
		try {
			herorepo.deleteAll();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void updateHero(HeroDTO dto) {
		try {
			Hero model = herorepo.findById(dto.getId()).get();
			PowerStats modelStats = powersrepo.findById(dto.getPowerStats()).get();

			
			model.setName(dto.getName());
			model.setPowerStats(dto.getPowerStats());
			model.setRace(Race.valueOf(dto.getRace()));
			model.setEnabled(dto.isEnabled());
			model.setCreated_at(dto.getCreated_at());
			model.setUpdated_at(dto.getUpdated_at());
			
			herorepo.save(model);
			
			modelStats.setAgility(dto.getAgility());
			modelStats.setCreated_at(dto.getCreated_atStats());
			modelStats.setIntelligence(dto.getIntelligence());
			modelStats.setDexterity(dto.getDexterity());
			modelStats.setStrength(dto.getStrength());
			modelStats.setUpdated_at(dto.getUpdated_atStats());
			
			powersrepo.save(modelStats);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public HeroCompareDTO compareHeroes(UUID idOne, UUID idTwo) {
		
		HeroCompareDTO heroDiff = new HeroCompareDTO();
		
		HeroDTO firstHero = herorepo.findHeroToCompare(idOne);
		HeroDTO secondHero = herorepo.findHeroToCompare(idTwo);
		
		heroDiff.setAgility(compareAttributes(firstHero.getAgility(), secondHero.getAgility()));
		heroDiff.setDexterity(compareAttributes(firstHero.getDexterity(), secondHero.getDexterity()));
		heroDiff.setIntelligence(compareAttributes(firstHero.getIntelligence(), secondHero.getIntelligence()));
		heroDiff.setStrength(compareAttributes(firstHero.getStrength(), secondHero.getStrength()));
		heroDiff.setHeroOneId(idOne);
		heroDiff.setHeroTwoId(idTwo);
		
		return heroDiff;
	}

	public int compareAttributes(int valOne, int valTwo) {		
		int diffValues;		
		diffValues = valOne - valTwo;		
		return diffValues;		
	}
	
}
