package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.dto.HeroDto;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.NameHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    @Autowired
    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final Long id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<HeroDto> create(@PathVariable Long id) {
        HeroDto dto = heroService.findById(id);
        return dto!= null ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("find-name")
    public ResponseEntity<HeroDto> create(@RequestBody NameHeroRequest name) {
        HeroDto dto = heroService.findByName(name.getName());
        return dto!= null ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }
}
