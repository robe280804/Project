package com.app_fitness.training_card_service.repository;

import com.app_fitness.training_card_service.model.TrainingCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<TrainingCard, String> {

    List<TrainingCard> findAllByUserId(String userId);
}
