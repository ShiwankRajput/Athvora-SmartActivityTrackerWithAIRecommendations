package com.shivnexEngineering.FitnessTrackerApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;
import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import com.shivnexEngineering.FitnessTrackerApplication.exception.ResourceNotFoundException;
import com.shivnexEngineering.FitnessTrackerApplication.exception.UserNotFoundException;
import com.shivnexEngineering.FitnessTrackerApplication.mapper.ActivityMapper;
import com.shivnexEngineering.FitnessTrackerApplication.repository.ActivityRepository;
import com.shivnexEngineering.FitnessTrackerApplication.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityService {

    private ActivityRepository activityRepository;
    private ActivityMapper activityMapper;
    private UserRepository userRepository;
    private RedisService redisService;

    public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper
        , UserRepository userRepository, RedisService redisService
    ){
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    public ActivityResponse createActivity(ActivityRequest activityRequest, String userId){

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User Not found"));

        Activity activity = Activity.builder()
            .user(user)
            .type(activityRequest.getType())
            .additionalMetrics(activityRequest.getAdditionalMetrics())
            .caloriesBurned(activityRequest.getCaloriesBurned())
            .duration(activityRequest.getDuration())
            .startTime(activityRequest.getStartTime())
            .build();

        Activity savedActivity = activityRepository.save(activity);
        ActivityResponse activityResponse = activityMapper.mapToActivityResponse(savedActivity);

        redisService.delete("activities:" + userId);

        return activityResponse;

    }

    public List<ActivityResponse> getActivity(String userId){

        String cacheKey = "activities:" + userId;

        List<ActivityResponse> cacheData = redisService.get(cacheKey, 
            new TypeReference<List<ActivityResponse>>(){});

        if(cacheData != null){
            log.info("Fetching activities from Redis");
            return cacheData;
        }

        log.info("Fetching activities from DB");
        List<Activity> activities = activityRepository.findByUserId(userId);
        
        List<ActivityResponse> activityResponses = activities.stream()
            .map(activityMapper::mapToActivityResponse)
            .collect(Collectors.toList());   
                
        redisService.save(cacheKey, activityResponses, 30);    

        return activityResponses;

    }

    public ActivityResponse getSpecificActivityByActivityId(String activityId, String userId){

        String cacheKey = "activity:" + activityId;

        ActivityResponse cacheData = redisService.get(cacheKey, ActivityResponse.class);

        if(cacheData != null){
            log.info("Fetching activity from Redis");
            return cacheData;
        }

        log.info("Fetching Activity from DB");
        Activity savedActivity = activityRepository.findByIdAndUserId(activityId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));

        ActivityResponse activityResponse = activityMapper.mapToActivityResponse(savedActivity);

        redisService.save(cacheKey, activityResponse, 30);

        return activityResponse;

    }

    public void deleteSpecificActivityByActitvityId(String activityId, String userId){

        Activity activity = activityRepository
            .findByIdAndUserId(activityId, userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Activity not found"));

        activityRepository.delete(activity);

        redisService.delete("activities:" + userId);
        redisService.delete("activity:" + activityId);

    }

    public ActivityResponse updateSpecActivityByActivityId(ActivityRequest activityRequest, 
        String activityId, String userId
    ){

        Activity savedActivity = activityRepository.findByIdAndUserId(activityId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Actitvity not found"));

        savedActivity.setType(activityRequest.getType());
        savedActivity.setAdditionalMetrics(activityRequest.getAdditionalMetrics());
        savedActivity.setDuration(activityRequest.getDuration());
        savedActivity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        savedActivity.setStartTime(activityRequest.getStartTime());

        Activity updatedActivity = activityRepository.save(savedActivity);

        redisService.delete("activities:" + userId);
        redisService.delete("activity:" + activityId);

        return activityMapper.mapToActivityResponse(updatedActivity);

    }

}
