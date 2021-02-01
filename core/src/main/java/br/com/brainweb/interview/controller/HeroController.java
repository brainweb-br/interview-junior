package br.com.brainweb.interview.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.dto.HeroCompareDTO;
import br.com.brainweb.interview.dto.HeroDTO;
import br.com.brainweb.interview.service.HeroService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/public/heroes")
public class HeroController {

	@Autowired	
	HeroService hservice;
	
	@GetMapping("/findHeroById/{id}")
	public ResponseEntity<HeroDTO> findHeroDataById(@PathVariable @RequestParam UUID id) {	
		
		HeroDTO dto = hservice.findHeroById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
		/* 
		 * Testar
		 * 
		 * Optional<HeroDTO> dto = hservice.findHeroById(id);
		 * 
		 * if(dto.isPresent()){
		 * 		ResponseEntity.ok(new HeroDTO(dto.get)); 
		 * }
		 * 
		 * return ResponseEntity.notFound().build();		 * 
		 * 
		 * */
		
	}
	
	@GetMapping("/findHeroByName/{name}")
	public ResponseEntity<HeroDTO> findHeroDataByName(@PathVariable @RequestParam String name) {	
		
		HeroDTO dto = hservice.findHeroByName(name);
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);		
	}
	
	@GetMapping("/findAllHeroes")
	public List<HeroDTO> findAllHeroesData() throws Exception{		
		return hservice.findAllHeroes();		
	}
	
	
	@DeleteMapping("/deleteHeroById/{id}")
	public ResponseEntity<?> removeColaborador(@PathVariable @RequestParam UUID id) {
		
		hservice.deleteHeroByID(id);
		
		return  ResponseEntity.status(HttpStatus.OK).body("Heroi " + id + " Removido!");
		
	}
	
	@DeleteMapping("/deleteHeroByName/{name}")
	public ResponseEntity<?> removeColaborador(@PathVariable @RequestParam String name) {		
		hservice.deleteHeroByName(name);
		
		return  ResponseEntity.status(HttpStatus.OK).body("Heroi " + name + " Removido!");
	}
	
	@DeleteMapping("/deleteAllHeroes")
	public ResponseEntity<?> removerTodos() {		
		hservice.deleteAllHeroes();	
		
		return  ResponseEntity.status(HttpStatus.OK).body("Heroi Removidos!");
	}
	
	@PostMapping("/saveHero")	
	public ResponseEntity<?> saveHeroData(@RequestBody HeroDTO dto) throws IOException{		
						
		hservice.saveHero(dto);	
		
		return  ResponseEntity.status(HttpStatus.CREATED).body("Heroi Cadastro!");
	}
	
	@PutMapping("/updateHero")
	public ResponseEntity<?> updateHeroData(@RequestBody HeroDTO dto) {
		hservice.updateHero(dto);
		
		return  ResponseEntity.status(HttpStatus.OK).body("Heroi Atualizado!");
	}
	
	@PostMapping("/compareHero")	
	public HeroCompareDTO compareHeroData(@RequestBody UUID valueOne, UUID valueTwo) throws IOException{		
		return hservice.compareHeroes(valueOne, valueTwo);		
	}
	
}
