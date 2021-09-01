package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.parser.FindGuyParser;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.FindGuyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Validated
                                         @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return ok(id.toString());
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<FindGuyParser> findGuyById(@Validated @PathVariable UUID id) {
        try {
            FindGuyRequest findGuyRequest = new FindGuyRequest();
            findGuyRequest.setId(id);

            return ok(heroService.findGuyByIdOrName(findGuyRequest, false));
        } catch (Exception exception) {
            return notFound().build();
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<FindGuyParser> findGuyByName(@Validated @PathVariable String name) {
        try {
            FindGuyRequest findGuyRequest = new FindGuyRequest();
            findGuyRequest.setName(name);

            return ok(heroService.findGuyByIdOrName(findGuyRequest, true));
        } catch (Exception exception) {
            return ok().build();
        }
    }
}
