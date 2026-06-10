package com.shivnexEngineering.FitnessTrackerApplication.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import com.shivnexEngineering.FitnessTrackerApplication.repository.UserRepository;
import com.shivnexEngineering.FitnessTrackerApplication.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    private JwtUtils jwtUtils;
    private UserRepository userRepository;

    public JwtFilter(JwtUtils jwtUtils, UserRepository userRepository){
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if(!jwtUtils.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String userId = jwtUtils.extractUserId(token);
        User user = userRepository.findById(userId).orElse(null);

        if(user != null && SecurityContextHolder.getContext().getAuthentication()==null){

            CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getId(), user.getEmail(), user.getPassword(), user.getRole()
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
                
    }

}
