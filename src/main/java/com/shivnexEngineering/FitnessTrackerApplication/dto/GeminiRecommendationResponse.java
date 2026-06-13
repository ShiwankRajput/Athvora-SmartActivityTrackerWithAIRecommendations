package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRecommendationResponse {

    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

}
