package br.com.brainweb.interview.model.request;

import br.com.brainweb.interview.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroesCompareRequest {

    private Long first;
    private Long second;

}
