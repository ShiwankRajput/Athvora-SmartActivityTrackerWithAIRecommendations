package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.shivnexEngineering.FitnessTrackerApplication.enums.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {

    private String id;
    private ActivityType type;
    private Map<String, Object> additionalMetrics;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
