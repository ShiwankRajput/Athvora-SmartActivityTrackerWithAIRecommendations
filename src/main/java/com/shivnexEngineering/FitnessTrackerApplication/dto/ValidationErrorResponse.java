package com.shivnexEngineering.FitnessTrackerApplication.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String path;
    private Map<String, String> validationErrors;

}
