package br.com.brainweb.interview.core.features.hero;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

	private final HeroService heroService;

	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Validated @RequestBody CreateHeroRequest createHeroRequest) {
		final UUID id = heroService.create(createHeroRequest);
		return created(URI.create(format("/api/v1/heroes/%s", id))).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Hero> getHeroById(@Validated @PathVariable("id") UUID id) {
		Hero hero = heroService.getHeroById(id);
		return ResponseEntity.ok().body(hero);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Hero>> getHerosByName(@Validated @PathVariable("name") String name) {
		List<Hero> heros = heroService.getHerosByName(name);
		return ResponseEntity.ok().body(heros);
	}

	@PutMapping("/update")
	public ResponseEntity<Integer> update(@Validated @RequestBody Hero hero) {
		Integer bool = heroService.updateHero(hero);
		return ResponseEntity.ok().body(bool);
	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<Integer> delete(@Validated @PathVariable("id") UUID id) {
		Integer bool = heroService.deleteHero(id);
		return ResponseEntity.ok().body(bool);
	}
}
