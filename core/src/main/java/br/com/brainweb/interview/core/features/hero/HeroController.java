package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.FindGuyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;
    private final PowerStatsService powerStatsService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Validated
                                         @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return ok(id.toString());
    }

    @PostMapping(value = "byid", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<FindGuyParser> findGuyById(@Validated @RequestBody FindGuyRequest findGuyRequest) {
        try {
            return ok(heroService.findGuyByIdOrName(findGuyRequest, false));
        } catch (Exception exception) {
            return notFound().build();
        }
    }

    @PostMapping(value = "byname", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<FindGuyParser> findGuyByName(@Validated @RequestBody FindGuyRequest findGuyRequest) {
        try {
            return ok(heroService.findGuyByIdOrName(findGuyRequest, true));
        } catch (Exception exception) {
            return ok().build();
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> updateGuyById(@Validated @RequestBody FindGuyParser findGuyParser) {
        try {
            // bruh, query error
            final UUID id = heroService.updateGuyById(findGuyParser);
            if (id == null)
                return unprocessableEntity().build();

            return powerStatsService.updateGuyById(findGuyParser, id) != null ? noContent().build() : unprocessableEntity().build();
        } catch (Exception exception) {
            return notFound().build();
        }
    }
}
