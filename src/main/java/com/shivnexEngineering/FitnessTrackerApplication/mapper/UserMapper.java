package com.shivnexEngineering.FitnessTrackerApplication.mapper;

import com.shivnexEngineering.FitnessTrackerApplication.dto.UserResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapToUserResponse(User user){

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }

}
