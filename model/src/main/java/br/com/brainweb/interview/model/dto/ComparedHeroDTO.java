package br.com.brainweb.interview.model.dto;

import br.com.brainweb.interview.model.PowerStats;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComparedHeroDTO {

    private UUID id;
    @JsonProperty("powerStats")
    private PowerStats powerStats;

}
