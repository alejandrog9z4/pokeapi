package com.agz.pokeapi.controller;

import com.agz.pokeapi.model.PokemonAllFilteredResponse;
import com.agz.pokeapi.model.PokemonBasicInformationResponse;
import com.agz.pokeapi.model.PokemonDetailResponse;
import com.agz.pokeapi.service.IPokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PokeApiControllerTest {

    @Mock
    private IPokemonService pokemonService;

    @InjectMocks
    private PokeApiController pokeApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pokeApiController).build();
    }

    @Test
    void getPokemon_ReturnsFilteredResponse() throws Exception {
        // Arrange
        PokemonAllFilteredResponse mockResponse = new PokemonAllFilteredResponse(
                10,
                "next-url",
                "prev-url",
                List.of(createMockPokemonResponse())
        );

        when(pokemonService.getAllPokemon(anyInt(), anyInt())).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/pokemon")
                        .param("offset", "0")
                        .param("limit", "12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.next").value("next-url"))
                .andExpect(jsonPath("$.previous").value("prev-url"))
                .andExpect(jsonPath("$.results[0].name").value("bulbasaur"));
    }

    @Test
    void getPokemonById_ReturnsPokemon() throws Exception {
        // Arrange
        when(pokemonService.getPokemonById("1")).thenReturn(createMockPokemonByIdResponse());

        // Act & Assert
        mockMvc.perform(get("/api/v1/pokemon/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("bulbasaur"));
    }

    @Test
    void getPokemonByName_ReturnsPokemon() throws Exception {
        // Arrange
        when(pokemonService.getPokemonById("bulbasaur")).thenReturn(createMockPokemonByIdResponse());

        // Act & Assert
        mockMvc.perform(get("/api/v1/pokemon/bulbasaur"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("bulbasaur"));
    }

    private PokemonBasicInformationResponse createMockPokemonResponse() {
        return new PokemonBasicInformationResponse(
                1,
                "bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                69,
                List.of("grass", "poison"),
                List.of("overgrow", "chlorophyll")
        );
    }
    private PokemonDetailResponse createMockPokemonByIdResponse() {
        return new PokemonDetailResponse(
                1,
                "bulbasaur",
                69,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                List.of("grass", "poison"),
                List.of("overgrow", "chlorophyll"),
                "",
                List.of("","")
        );
    }
}
