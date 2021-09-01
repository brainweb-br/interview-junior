package br.com.brainweb.interview.model.parser;

import br.com.brainweb.interview.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindGuyParser {
    @NotNull
    private UUID id;

    @NotEmpty
    private String name;

    private Race race;

    @NotNull
    private int strength;

    @NotNull
    private int agility;

    @NotNull
    private int dexterity;

    @NotNull
    private int intelligence;
}
