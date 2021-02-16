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
import static org.springframework.http.ResponseEntity.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@Validated @RequestParam UUID id) {
        try{
            HeroDTO hero = heroService.findById(id);
            return status(HttpStatus.OK).body(hero);
        } catch (EmptyResultDataAccessException e) {
            return notFound().build();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findByName(@Validated @RequestParam String name) {
        try {
            return ok(heroService.findByName(name));
        } catch (EmptyResultDataAccessException e) {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHero(
        @Validated @RequestBody HeroDTO hero, @Validated @PathVariable UUID id
    ) {
        try {
            heroService.updateHero(id, hero);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Validated @PathVariable UUID id) {
        try {
            heroService.delete(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return notFound().build();
        }
    }

    @GetMapping("/compare/{id1}/{id2}")
    public ResponseEntity<?> compare(
        @Validated @PathVariable("id1") UUID id1, @Validated @PathVariable("id2") UUID id2
    ) {
        HeroesDTO heroes = heroService.compare(id1, id2);

        if (heroes == null) {
            return notFound().build();
        }

        return ok(heroes);
    }
}
