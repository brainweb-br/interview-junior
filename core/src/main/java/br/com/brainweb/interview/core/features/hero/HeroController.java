package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroWithStatsDto;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        final HeroWithStatsDto response = heroService.findById(id);
        if(response != null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object Not Found!!!");
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findById(@PathVariable String name){
        final HeroWithStatsDto response = heroService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
