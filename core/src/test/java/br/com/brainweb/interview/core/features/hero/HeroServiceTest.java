package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.enums.Race;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class HeroServiceTest {

    @InjectMocks
    private HeroService service;

    @Mock
    private HeroRepository repository;

    @Test
    public void shouldBeReturnAnOptionalContainingTheWantedHeroIfItExists() {
        //given
        String wantedHeroId = "1b9f3125-b59d-49c0-90df-2cf6be63e39f";
        Mockito.when(repository.findById(UUID.fromString(wantedHeroId))).thenReturn(this.createHero());

        //when
        Optional<Hero> wantedHero = this.service.getById(wantedHeroId);

        //then
        Assert.assertTrue(wantedHero.isPresent());
        Assert.assertNotNull(wantedHero.get());
    }

    @Test
    public void shouldBeReturnANullableOptionalIfWantedHeroDoesNotExists() {
        //given
        String inexistentHeroId = "93918f6f-861e-4cf9-b6f9-c2b78a55e7a3";
        Mockito.when(repository.findById(UUID.fromString(inexistentHeroId))).thenThrow(new EmptyResultDataAccessException(0));

        // when
        Optional<Hero> wantedHero = this.service.getById(inexistentHeroId);

        // then
        Assert.assertFalse(wantedHero.isPresent());
    }

    private Hero createHero() {
        return Hero.builder()
                .id(UUID.fromString("1b9f3125-b59d-49c0-90df-2cf6be63e39f"))
                .name("Martian Manhunter")
                .powerStatsId(UUID.fromString("d1f59cb0-a999-41a9-b5bd-7f0d821a819f"))
                .race(Race.ALIEN)
                .build();
    }
}