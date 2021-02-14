package br.com.brainweb.interview.model.dto;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.enums.Race;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroDTO {

    @NotNull
    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    private Race race;

    @NotNull
    private boolean enabled;

    @JsonProperty("powerStats")
    @NotNull
    private PowerStats powerStats;
}
