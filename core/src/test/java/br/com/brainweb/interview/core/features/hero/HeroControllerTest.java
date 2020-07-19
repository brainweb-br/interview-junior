package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.enums.Race;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeroController.class)
class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HeroService heroService;

    @BeforeEach
    public void initTest() {
        when(heroService.create(any())).thenReturn(UUID.randomUUID());
    }

    @Test
    void createAHeroWithAllRequiredArguments() throws Exception {
        //given
        // Convert the hero request into a string JSON format stub.
        final String body = objectMapper.writeValueAsString(createHeroRequest());

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        //then
        resultActions.andExpect(status().isCreated()).andExpect(header().exists("Location"));
        verify(heroService, times(1)).create(any());
    }

    @Test
    void shouldReturnTheHeroObjectInResponseBodyWhenRequestIdMatchWithAHeroIdInDatabase() throws Exception {

        //given
        String wantedHeroId = "1b9f3125-b59d-49c0-90df-2cf6be63e39f";
        Mockito.when(heroService.getById(wantedHeroId)).thenReturn(Optional.of(this.createHero()));

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + wantedHeroId));

        //then

        resultActions.andExpect(status().is2xxSuccessful()).andExpect(mvcResult -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            Assert.assertEquals(mvcResult.getResponse().getContentAsString(), mapper.writeValueAsString(createHero()));
        });
    }

    @Test
    void shouldReturnAnEmptyObjectInResponseBodyWhenRequestIdDoesNotMatchWithAnyHeroIdInDatabase() throws Exception {

        //given
        String inexistentHeroId = "93918f6f-861e-4cf9-b6f9-c2b78a55e7a3";
        Mockito.when(heroService.getById(inexistentHeroId)).thenReturn(Optional.empty());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + inexistentHeroId));

        //then
        resultActions.andExpect(status().is4xxClientError()).andExpect(mvcResult -> {
            Assert.assertEquals(mvcResult.getResponse().getContentAsString(), "");
        });
    }

    private CreateHeroRequest createHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Batman")
                .agility(5)
                .dexterity(8)
                .strength(6)
                .intelligence(10)
                .race(Race.HUMAN)
                .build();
    }

    private Hero createHero() {
        return Hero.builder()
                .id(UUID.fromString("1b9f3125-b59d-49c0-90df-2cf6be63e39f"))
                .name("Martian Manhunter")
                .powerStatsId(UUID.fromString("d1f59cb0-a999-41a9-b5bd-7f0d821a819f"))
                .race(Race.ALIEN)
                .createdAt(null)
                .updatedAt(null)
                .enabled(true)
                .build();
    }
}