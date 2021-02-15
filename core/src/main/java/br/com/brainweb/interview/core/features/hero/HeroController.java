package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroCompareDTO;
import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.dto.HeroesDTO;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try{
            HeroDTO hero = heroService.findById(UUID.fromString(id));
            return status(HttpStatus.OK).body(hero);
        } catch (EmptyResultDataAccessException e) {
            return status(HttpStatus.NOT_FOUND).body("No Hero found for the given id");
        }
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> findByName(@Validated @PathVariable String name) {
        try {
            return status(HttpStatus.OK).body(heroService.findByName(name));
        } catch (EmptyResultDataAccessException e) {
            return status(HttpStatus.OK).body("");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHero(
        @Validated @RequestBody HeroDTO hero, @Validated @PathVariable String id
    ) {
        try {
            hero.setId(UUID.fromString(id));
            heroService.updateHero(hero);
            return status(HttpStatus.NO_CONTENT).body("");
        } catch (EmptyResultDataAccessException e) {
            return status(HttpStatus.NOT_FOUND).body("Cannot update Hero for the given id");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Validated @PathVariable String id) {
        try {
            heroService.delete(UUID.fromString(id));
            return status(HttpStatus.NO_CONTENT).body("");
        } catch (EmptyResultDataAccessException e) {
            return status(HttpStatus.NOT_FOUND).body("Cannot delete Hero for the given id");
        }
    }

    @GetMapping("/compare/{id1}/{id2}")
    public ResponseEntity<?> compare(
        @Validated @PathVariable("id1") String id1, @Validated @PathVariable("id2") String id2
    ) {
        HeroesDTO heroes = heroService.compare(UUID.fromString(id1), UUID.fromString(id2));

        if (heroes == null) {
            return status(HttpStatus.NO_CONTENT).body("");
        }

        return status(HttpStatus.OK).body(heroes);
    }
}
