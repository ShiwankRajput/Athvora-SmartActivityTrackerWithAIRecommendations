package com.shivnexEngineering.FitnessTrackerApplication.repository;

import com.shivnexEngineering.FitnessTrackerApplication.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

}
