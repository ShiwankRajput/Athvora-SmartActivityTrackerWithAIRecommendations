package com.shivnexEngineering.FitnessTrackerApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;
import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
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
            .orElseThrow(() -> new RuntimeException("Cannot found user by user id"));

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

        redisService.delete("cacheKey:" + userId);

        return activityResponse;

    }

    public List<ActivityResponse> getActivity(String userId){

        String cacheKey = "activities:" + userId;

        Object cacheData = redisService.get(cacheKey);

        if(cacheData != null){
            log.info("Fetching activities from Redis");
            return (List<ActivityResponse>) cacheData;
        }

        log.info("Fetching activities from DB");
        List<Activity> activities = activityRepository.findByUserId(userId);
        
        List<ActivityResponse> activityResponses = activities.stream()
            .map(activityMapper::mapToActivityResponse)
            .collect(Collectors.toList());   
                
        redisService.save(cacheKey, activityResponses, 15);    

        return activityResponses;

    }

}
