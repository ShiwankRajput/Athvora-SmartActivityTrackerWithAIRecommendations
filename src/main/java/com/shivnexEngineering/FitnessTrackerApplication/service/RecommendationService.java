package com.shivnexEngineering.FitnessTrackerApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Recommendation;
import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import com.shivnexEngineering.FitnessTrackerApplication.mapper.RecommendationMapper;
import com.shivnexEngineering.FitnessTrackerApplication.repository.ActivityRepository;
import com.shivnexEngineering.FitnessTrackerApplication.repository.RecommendationRepository;
import com.shivnexEngineering.FitnessTrackerApplication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationMapper recommendationMapper;

    public RecommendationResponse getGeneratedRecommendation(RecommendationRequest request, String userId,
        String activity_id
    ){
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Cannot found User by id -> " + userId));

        Activity activity = activityRepository.findById(activity_id)
            .orElseThrow(() -> new RuntimeException("Cannot found Activity by id -> " + activity_id));
          
        Recommendation recommendation = Recommendation.builder()
            .user(user)
            .activity(activity)
            .improvements(request.getImprovements())
            .suggestions(request.getSuggestions())
            .safety(request.getSafety())
            .build();

        recommendationRepository.save(recommendation);    

        RecommendationResponse recommendationResponse
            = recommendationMapper.mapToRecommendationResponse(recommendation); 
            
        return recommendationResponse;    

    }

    public List<RecommendationResponse> getRecommendationByUserId(String userId){

        userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Cannot find User by id -> " + userId));

        List<Recommendation> recommendation = recommendationRepository.findByUserId(userId);

        return recommendation.stream()
            .map(recommendationMapper::mapToRecommendationResponse).collect(Collectors.toList());

    }

    public List<RecommendationResponse> getRecommendationByActivityId(String activityId){

        activityRepository.findById(activityId)
            .orElseThrow(() -> new RuntimeException("Cannot find Activity by id -> " + activityId));

        List<Recommendation> recommendations = recommendationRepository.findByActivityId(activityId);
        
        return recommendations.stream()
            .map(recommendationMapper::mapToRecommendationResponse).collect(Collectors.toList());

    }

}
