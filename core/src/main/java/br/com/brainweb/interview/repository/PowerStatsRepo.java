package br.com.brainweb.interview.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.model.PowerStats;


@Repository
@Transactional
public interface PowerStatsRepo extends JpaRepository<PowerStats, UUID>{

	@Query(value = "select max(p.id)"
			+ "from PowerStats p") 		
	public UUID findMaxPowerStats();	
	
	
}
