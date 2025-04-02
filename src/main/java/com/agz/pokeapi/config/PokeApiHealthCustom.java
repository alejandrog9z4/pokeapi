package com.agz.pokeapi.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PokeApiHealthCustom implements HealthIndicator {
    @Override
    public Health health() {
        try{
            return Health.up().withDetail("PokeApi", "PokeApi Status Up").build();
        }catch (Exception e){
            return Health.down().withDetail("PokeApi", "Api down, fail start").build();
        }
    }
}
