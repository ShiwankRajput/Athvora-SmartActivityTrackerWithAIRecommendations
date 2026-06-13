package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.util.Map;

import com.shivnexEngineering.FitnessTrackerApplication.enums.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityGeminiRequest {

    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private Map<String, Object> additionalMetrics;

}
