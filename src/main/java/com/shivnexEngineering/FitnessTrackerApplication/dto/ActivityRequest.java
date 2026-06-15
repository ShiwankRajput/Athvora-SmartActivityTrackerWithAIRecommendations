package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.shivnexEngineering.FitnessTrackerApplication.enums.ActivityType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    @NotNull(message = "Activity type cannot be empty")
    private ActivityType type;

    private Map<String, Object> additionalMetrics;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Calories Burned is required")
    @Positive(message = "Caloried Burned must be greater than 0")
    private Integer caloriesBurned;

    @NotNull(message = "Start time is required")
    @PastOrPresent(message = "Start time cannot be in future")
    private LocalDateTime startTime;

}
