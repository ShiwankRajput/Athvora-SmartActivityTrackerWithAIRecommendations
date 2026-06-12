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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationMapper recommendationMapper;
    private final RedisService redisService;

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

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);    

        RecommendationResponse recommendationResponse
            = recommendationMapper.mapToRecommendationResponse(savedRecommendation); 

        redisService.delete("user_recommendations:" + userId);  
        redisService.delete("activity_recommendations:" + activity_id);  
            
        return recommendationResponse;    

    }

    public List<RecommendationResponse> getRecommendationByUserId(String userId){

        String cacheKey = "user_recommendations:" + userId;

        Object cacheData = redisService.get(cacheKey);

        if(cacheData != null){
            log.info("Fetching recommendations from Redis");
            return (List<RecommendationResponse>) cacheData;
        }

        log.info("Fetching recommendations from DB");
        userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Cannot find User by id -> " + userId));

        List<Recommendation> recommendation = recommendationRepository.findByUserId(userId);

        List<RecommendationResponse> recommendationResponses = recommendation.stream()
            .map(recommendationMapper::mapToRecommendationResponse).collect(Collectors.toList());

        redisService.save(cacheKey, recommendationResponses, 20);
        
        return recommendationResponses;

    }

    public List<RecommendationResponse> getRecommendationByActivityId(String activityId){

        String cacheKey = "activity_recommendations:" + activityId;

        Object cacheData = redisService.get(cacheKey);

        if(cacheData != null){
            System.out.println("fetching from Redis");
            return (List<RecommendationResponse>) cacheData;
        }

        System.out.println("Fetching from DB");
        activityRepository.findById(activityId)
            .orElseThrow(() -> new RuntimeException("Cannot find Activity by id -> " + activityId));

        List<Recommendation> recommendations = recommendationRepository.findByActivityId(activityId);
        
        List<RecommendationResponse> recommendationResponses = recommendations.stream()
            .map(recommendationMapper::mapToRecommendationResponse).collect(Collectors.toList());

        redisService.save(cacheKey, recommendationResponses, 20);

        return recommendationResponses;    

    }

}
