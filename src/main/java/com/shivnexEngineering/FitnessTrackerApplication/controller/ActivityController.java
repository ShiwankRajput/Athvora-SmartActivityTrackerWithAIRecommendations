package com.shivnexEngineering.FitnessTrackerApplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityResponse;
import com.shivnexEngineering.FitnessTrackerApplication.security.CustomUserDetails;
import com.shivnexEngineering.FitnessTrackerApplication.service.ActivityService;

@RestController
@RequestMapping("/api/users")
public class ActivityController {

    private ActivityService activityService;
    
    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    // Saves Activity
    @PostMapping("/activity")
    public ResponseEntity<ActivityResponse> addActivity(@RequestBody ActivityRequest activityRequest,
        Authentication authentication
    ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(activityService.createActivity(activityRequest, customUserDetails.getId()));
    }

    // Get Activities - by userId
    @GetMapping("/activity")
    public ResponseEntity<List<ActivityResponse>> getActivity(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(activityService.getActivity(customUserDetails.getId()));
    }

}
