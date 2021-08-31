package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createHero(@Validated
                                           @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(heroService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterByName(@RequestParam String name) {
        List<HeroDTO> heroes = heroService.filterByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(heroes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroDTO> updateHero(@PathVariable UUID id, @RequestBody @Valid HeroDTO updatedHero) {
        return ResponseEntity.status(HttpStatus.OK).body(heroService.updateHeroById(id, updatedHero));
    }

    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable UUID id) {
        heroService.deleteById(id);
    }

    @GetMapping("/compare")
    public ResponseEntity<?> compareHeroes(@RequestParam UUID hero1, @RequestParam UUID hero2) {
        return ResponseEntity.ok().body(heroService.compareHeroes(hero1, hero2));
    }
}
