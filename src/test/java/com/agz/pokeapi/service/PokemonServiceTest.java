package com.agz.pokeapi.service;

import com.agz.pokeapi.client.PokeApi;
import com.agz.pokeapi.model.*;
import com.agz.pokeapi.utils.UtilClientPokeApi;
import com.agz.pokeapi.utils.UtilsPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokeApi pokeApi;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pokemonService, "defaultLimit", 12);
    }


    @Test
    void getAllPokemon_Success() {
        PokemonAllResponse mockResponse = new PokemonAllResponse(
                10,
                "https://pokeapi.co/api/v2/pokemon?offset=0&limit=12",
                null,
                List.of(new NamedAPIResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        );

        PokemonApiInfo mockPokemonInfo = createMockPokemonInfo();
        when(pokeApi.getPokemon(0, 12)).thenReturn(mockResponse);
        when(pokeApi.getPokemonById(1)).thenReturn(mockPokemonInfo);

        // Act
        PokemonAllFilteredResponse result = pokemonService.getAllPokemon(0, 12);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.count());
        assertFalse(result.results().isEmpty());
        assertEquals("bulbasaur", result.results().get(0).name());
    }

    @Test
    void getAllPokemon_WithDifferentLimit_ClearsCache() {
        // Arrange
        Cache mockCache = mock(Cache.class);
        when(cacheManager.getCache("pokemonCache")).thenReturn(mockCache);

        PokemonAllResponse mockResponse = new PokemonAllResponse(
                10,
                "next-url",
                "prev-url",
                List.of(new NamedAPIResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        );
        when(pokeApi.getPokemon(0, 20)).thenReturn(mockResponse);
        when(pokeApi.getPokemonById(1)).thenReturn(createMockPokemonInfo());

        // Act
        pokemonService.getAllPokemon(0, 20);

        // Assert
        verify(mockCache).clear();
    }

    @Test
    void getPokemonById_Success() {

        PokemonApiInfo mockPokemonInfo = createMockPokemonInfo();
        PokemonSpeciesDetail mockPokemonSpeciesDetail = createMockPokemonSpeciesDetail();
        EvolutionChain mockEvolutionChain = createMockEvolutionChain();

        when(pokeApi.getPokemonById(1)).thenReturn(mockPokemonInfo);
        when(pokeApi.getPokemonSpecies(1)).thenReturn(mockPokemonSpeciesDetail);
        when(pokeApi.getEvolutionChain(1)).thenReturn(mockEvolutionChain);

        PokemonDetailResponse result = pokemonService.getPokemonById("1");
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals("bulbasaur", result.name());
    }


    @Test
    void getPokemonByName_Success() {
        PokemonApiInfo mockPokemonInfo = createMockPokemonInfo();
        PokemonSpeciesDetail mockPokemonSpeciesDetail = createMockPokemonSpeciesDetail();
        EvolutionChain mockEvolutionChain = createMockEvolutionChain();

        when(pokeApi.getPokemonByName("bulbasaur")).thenReturn(mockPokemonInfo);
        when(pokeApi.getPokemonSpecies(1)).thenReturn(mockPokemonSpeciesDetail);
        when(pokeApi.getEvolutionChain(1)).thenReturn(mockEvolutionChain);

        PokemonDetailResponse result = pokemonService.getPokemonById("bulbasaur");
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals("bulbasaur", result.name());
    }

    private PokemonApiInfo createMockPokemonInfo() {
        return new PokemonApiInfo(
                1,
                "bulbasaur",
                69,
                true,
                7,
                69,
                1,
                List.of(new PokemonAbility(false, 1, new NamedAPIResource("overgrow", "url"))),
                List.of(new NamedAPIResource("bulbasaur", "url")),
                List.of(),
                List.of(),
                "url",
                List.of(),
                new NamedAPIResource("test", "url"),
                new PokemonSprites("front_default", "back_default", "front_female", "back_female",
                        "front_shiny", "back_shiny", "front_shiny_female", "back_shiny_female"),
                new PokemonCries("latest", "classic"),
                List.of(),
                List.of(new PokemonTypes(1, new NamedAPIResource("grass", "url"))),
                List.of()
        );
    }

    private PokemonSpeciesDetail createMockPokemonSpeciesDetail(){
        return new PokemonSpeciesDetail(
                1,
                "bulbasaur",
                1,
                1,
                1,
                1,
                false,
                false,
                false,
                1,
                false,
                false,
                new NamedAPIResource("",""),
                List.of(new PokemonSpeciesDexEntry(1,new NamedAPIResource("",""))),
                List.of(new NamedAPIResource("","")),
                new NamedAPIResource("",""),
                new NamedAPIResource("",""),
                new NamedAPIResource("",""),
                new ApiResource("https://pokeapi.co/api/v2/evolution-chain/1/"),
                new NamedAPIResource("",""),
                new NamedAPIResource("",""),
                List.of(new Name("",new NamedAPIResource("",""))),
                List.of(new PalParkEncounterArea(1,1,new NamedAPIResource("",""))),
                List.of(new PokemonFlavorText("",new NamedAPIResource("",""),new NamedAPIResource("",""))),
                List.of(new Description("",new NamedAPIResource("",""))),
                List.of(new Genus("",new NamedAPIResource("",""))),
                List.of(new PokemonSpeciesVariety(new NamedAPIResource("",""),true))
        );
    }
    private EvolutionChain createMockEvolutionChain() {
        return new EvolutionChain(
                 1,
                new NamedAPIResource("",""),
                new ChainLink(
                        false,
                        new NamedAPIResource("",""),
                        List.of(
                                new EvolutionDetail(
                                        new NamedAPIResource("",""),
                                        new NamedAPIResource("",""),
                                        1,
                                        new NamedAPIResource("",""),
                                        new NamedAPIResource("",""),
                                        new NamedAPIResource("",""),
                                        new NamedAPIResource("",""),
                                        1,
                                        1,
                                        1,
                                        1,
                                        false,
                                        new NamedAPIResource("",""),
                                        new NamedAPIResource("",""),
                                        1,
                                        "time_of_day",
                                        new NamedAPIResource("",""),
                                        false
                                )
                        ),
                        List.of(
                                new ChainLink(
                                        false,
                                        new NamedAPIResource("",""),
                                        List.of(
                                                new EvolutionDetail(
                                                        new NamedAPIResource("",""),
                                                        new NamedAPIResource("",""),
                                                        1,
                                                        new NamedAPIResource("",""),
                                                        new NamedAPIResource("",""),
                                                        new NamedAPIResource("",""),
                                                        new NamedAPIResource("",""),
                                                        1,
                                                        1,
                                                        1,
                                                        1,
                                                        false,
                                                        new NamedAPIResource("",""),
                                                        new NamedAPIResource("",""),
                                                        1,
                                                        "time_of_day",
                                                        new NamedAPIResource("",""),
                                                        false
                                                )
                                        ),
                                        List.of()

                                )

                        )

                )
        );

    }
}
