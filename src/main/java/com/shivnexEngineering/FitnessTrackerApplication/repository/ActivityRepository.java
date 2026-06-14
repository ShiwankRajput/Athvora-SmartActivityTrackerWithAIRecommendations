package com.shivnexEngineering.FitnessTrackerApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivnexEngineering.FitnessTrackerApplication.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String>{

    List<Activity> findByUserId(String userId);

    Optional<Activity> findByIdAndUserId(String activityid, String userId);

    void deleteByIdAndUserId(String activityId, String userId);

}
