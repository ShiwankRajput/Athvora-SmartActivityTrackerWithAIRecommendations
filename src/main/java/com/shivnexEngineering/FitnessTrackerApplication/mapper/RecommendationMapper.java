package com.shivnexEngineering.FitnessTrackerApplication.mapper;

import org.springframework.stereotype.Component;

import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationResponse;
import com.shivnexEngineering.FitnessTrackerApplication.dto.RecommendationUserResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.Recommendation;

@Component
public class RecommendationMapper {

    public RecommendationResponse mapToRecommendationResponse(Recommendation recommendation){

        RecommendationResponse recommendationResponse = new RecommendationResponse();

        RecommendationUserResponse user = new RecommendationUserResponse(
            recommendation.getUser().getFirstName(),
            recommendation.getUser().getLastName()
        );

        recommendationResponse.setId(recommendation.getId());
        recommendationResponse.setType(recommendation.getType());
        recommendationResponse.setUser(user);
        recommendationResponse.setSuggestions(recommendation.getSuggestions());
        recommendationResponse.setActivity(recommendation.getActivity());
        recommendationResponse.setImprovements(recommendation.getImprovements());
        recommendationResponse.setRecommendation(recommendation.getRecommendation());
        recommendationResponse.setSafety(recommendation.getSafety());
        recommendationResponse.setCreatedAt(recommendation.getCreatedAt());
        recommendationResponse.setUpdatedAt(recommendation.getUpdatedAt());

        return recommendationResponse;

    }

}
