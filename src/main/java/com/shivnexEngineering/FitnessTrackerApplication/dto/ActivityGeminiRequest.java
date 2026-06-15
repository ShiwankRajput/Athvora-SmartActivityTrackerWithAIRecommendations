package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.util.Map;

import com.shivnexEngineering.FitnessTrackerApplication.enums.ActivityType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityGeminiRequest {

    @NotNull(message = "Activity type is required, this cannot be empty")
    private ActivityType type;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Calories burned is required")
    @Positive(message = "Caloried burned must be greater than 0")
    private Integer caloriesBurned;
    
    private Map<String, Object> additionalMetrics;

}
