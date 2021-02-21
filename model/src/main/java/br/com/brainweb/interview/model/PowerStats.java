package br.com.brainweb.interview.model;

import br.com.brainweb.interview.model.request.CreateHeroRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    @JsonIgnore private Date createdAtDate;
    @JsonIgnore private Date updatedAtDate;

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(createdAtDate);
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(updatedAtDate);
    }

    public PowerStats(CreateHeroRequest createHeroRequest) {
        this.strength = createHeroRequest.getStrength();
        this.agility = createHeroRequest.getAgility();
        this.dexterity = createHeroRequest.getDexterity();
        this.intelligence = createHeroRequest.getIntelligence();
    }
}
