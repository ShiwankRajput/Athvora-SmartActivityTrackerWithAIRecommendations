package com.shivnexEngineering.FitnessTrackerApplication.mapper;

import org.springframework.stereotype.Component;

import com.shivnexEngineering.FitnessTrackerApplication.dto.ActivityResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;

@Component
public class ActivityMapper {

    public ActivityResponse mapToActivityResponse(Activity activity){

        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(activity.getId());
        activityResponse.setType(activity.getType());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());

        return activityResponse;

    }

}
