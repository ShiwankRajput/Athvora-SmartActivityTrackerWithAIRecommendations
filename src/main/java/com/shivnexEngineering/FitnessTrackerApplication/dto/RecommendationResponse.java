package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private String id;
    private String type;
    private RecommendationUserResponse user;
    private Activity activity;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
