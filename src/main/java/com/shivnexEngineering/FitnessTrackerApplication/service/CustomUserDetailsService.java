package com.shivnexEngineering.FitnessTrackerApplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import com.shivnexEngineering.FitnessTrackerApplication.repository.UserRepository;
import com.shivnexEngineering.FitnessTrackerApplication.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CustomUserDetails customUserDetails = new CustomUserDetails(
            user.getId(), user.getEmail(), user.getPassword(), user.getRole()
        );    

        return customUserDetails;
    }

}
