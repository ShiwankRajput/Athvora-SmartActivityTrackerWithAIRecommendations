package com.shivnexEngineering.FitnessTrackerApplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationResponse;
import com.shivnexEngineering.FitnessTrackerApplication.security.CustomUserDetails;
import com.shivnexEngineering.FitnessTrackerApplication.service.RecommendationService;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    // Generate Recommendation
    @PostMapping("/generate/{activity_id}")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@RequestBody RecommendationRequest request,
        Authentication authentication, @PathVariable String activity_id
    ){

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RecommendationResponse recommendationResponse = recommendationService.getGeneratedRecommendation(request, 
            customUserDetails.getId(), activity_id
        );

        return ResponseEntity.ok(recommendationResponse);
    }

    // Get Recommendations by UserId
    @GetMapping("/user")
    public ResponseEntity<List<RecommendationResponse>> getRecommendationForUser(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(recommendationService.getRecommendationByUserId(customUserDetails.getId()));
    }

    // Get Recommendations by ActivityId
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendationForActivity(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getRecommendationByActivityId(activityId));
    }

}
