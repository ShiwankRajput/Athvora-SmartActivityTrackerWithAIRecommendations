package com.shivnexEngineering.FitnessTrackerApplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // Get Specific activity - by activityId
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponse> getSpecificActivity(@PathVariable String activityId, Authentication authentication){
        CustomUserDetails customUserDetails =(CustomUserDetails) authentication.getPrincipal();
        ActivityResponse activityResponse = activityService.getSpecificActivityByActivityId(activityId, customUserDetails.getId());
        return ResponseEntity.ok(activityResponse);
    }    

    // Delete Specific activity - by activityId
    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteSpecificActivity(@PathVariable String activityId, Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        activityService.deleteSpecificActivityByActitvityId(activityId, customUserDetails.getId());
        return ResponseEntity.noContent().build();
    }

    // Update Specific activity - by activityId
    @PutMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponse> updateSpecificActivity(@RequestBody ActivityRequest activityRequest,
         @PathVariable String activityId, Authentication authentication
    ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        ActivityResponse activityResponse = activityService.updateSpecActivityByActivityId(activityRequest, activityId, 
            customUserDetails.getId()
        );
        return ResponseEntity.ok(activityResponse);
    }

}
