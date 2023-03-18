package br.com.brainweb.interview.core.features.hero;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.enums.Race;
import br.com.brainweb.interview.model.request.CreateHeroRequest;

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
    // given
    // Convert the hero request into a string JSON format stub.
    final String body = objectMapper.writeValueAsString(createHeroRequest());

    // when
    final ResultActions resultActions = mockMvc
        .perform(post("/api/v1/heroes").contentType(MediaType.APPLICATION_JSON).content(body));

    // then
    resultActions.andExpect(status().isCreated()).andExpect(header().exists("Location"));
    verify(heroService, times(1)).create(any());
  }

  private CreateHeroRequest createHeroRequest() {
    return CreateHeroRequest.builder().name("Batman").agility(5).dexterity(8).strength(6)
        .intelligence(10).race(Race.HUMAN).build();
  }

  @Test
  void shouldGetHeroById() throws Exception {
    Hero hero = new Hero(new CreateHeroRequest("Anonymous", Race.ALIEN, 7, 9, 8, 9),
        createPowerStatsRequest().getId());

    when(heroService.getHeroById(UUID.fromString("d1a03fc7-f0ed-41a2-960f-4bb21a61cd52")))
        .thenReturn(hero);

    MvcResult result = mockMvc
        .perform(get("/api/v1/heroes/d1a03fc7-f0ed-41a2-960f-4bb21a61cd52")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.power_stats_id").value("d1a03fc7-f0ed-41a2-960f-4bb21a61cd51"))
        .andReturn();

    String strResult = result.getResponse().getContentAsString();
    System.out.println(strResult);
  }

  @Test
  void shouldGetHeroByName() throws Exception {
    Hero hero = new Hero(new CreateHeroRequest("Anonymous", Race.ALIEN, 7, 9, 8, 9),
        createPowerStatsRequest().getId());

    List<Hero> heros = new ArrayList<>();
    heros.add(hero);

    when(heroService.getHerosByName("Anonymous")).thenReturn(heros);

    MvcResult result =
        mockMvc.perform(get("/api/v1/heroes/name/Anonymous").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$[*].power_stats_id").value("d1a03fc7-f0ed-41a2-960f-4bb21a61cd51"))
            .andReturn();

    String strResult = result.getResponse().getContentAsString();
    System.out.println(strResult);
  }

  private PowerStats createPowerStatsRequest() {
    return PowerStats.builder().id(UUID.fromString("d1a03fc7-f0ed-41a2-960f-4bb21a61cd51"))
        .strength(7).agility(5).dexterity(8).intelligence(9).createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
  }
}
