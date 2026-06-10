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

    @Service
    public class ActivityService {

        private ActivityRepository activityRepository;
        private ActivityMapper activityMapper;
        private UserRepository userRepository;

        public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper
            , UserRepository userRepository
        ){
            this.activityRepository = activityRepository;
            this.activityMapper = activityMapper;
            this.userRepository = userRepository;
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

            return activityResponse;
        }

        public List<ActivityResponse> getActivity(String userId){

            List<Activity> activities = activityRepository.findByUserId(userId);
        
            List<ActivityResponse> activityResponses = activities.stream()
                .map(activityMapper::mapToActivityResponse)
                .collect(Collectors.toList());    

            return activityResponses;

        }


    }
