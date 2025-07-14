package com.app_fitness.activity_service.repository;

import com.app_fitness.activity_service.model.TrainingPreferences;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PreferencesRepository extends MongoRepository<TrainingPreferences, String> {
    List<TrainingPreferences> findAllByUserId(String userId);
}
