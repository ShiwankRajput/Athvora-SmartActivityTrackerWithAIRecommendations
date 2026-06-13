package com.shivnexEngineering.FitnessTrackerApplication.geminAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GeminiConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
            .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
