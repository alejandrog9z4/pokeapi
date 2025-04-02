package com.agz.pokeapi.controller;

import com.agz.pokeapi.model.PokemonAllFilteredResponse;
import com.agz.pokeapi.model.PokemonBasicInformationResponse;
import com.agz.pokeapi.model.PokemonDetailResponse;
import com.agz.pokeapi.service.IPokemonService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@Tag(name = "Pokemon", description = "Pokemon APIs")
public class PokeApiController {
   Logger logger = org.slf4j.LoggerFactory.getLogger(PokeApiController.class);
    @Autowired
    private IPokemonService pokemonService;

    @Operation(summary = "Get all Pokemon", description = "Returns a paginated list of Pokemon")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon list"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping(value= "", produces = "application/json")

    public ResponseEntity<PokemonAllFilteredResponse> getPokemon(
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "12") int limit)
    {
        PokemonAllFilteredResponse data = pokemonService.getAllPokemon(offset, limit);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get Pokemon by ID", description = "Returns a Pokemon by its ID or name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pokemon found"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PokemonDetailResponse> getPokemonById(
            @Parameter(description = "Pokemon ID or name") @PathVariable String id){
        logger.info("id: "+id);
        PokemonDetailResponse pokemon =pokemonService.getPokemonById(id);
        return ResponseEntity.ok(pokemon);
    }

}
