package br.com.brainweb.interview.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareGuyRequest {
    @NotNull
    private UUID id1;

    @NotNull
    private UUID id2;
}
