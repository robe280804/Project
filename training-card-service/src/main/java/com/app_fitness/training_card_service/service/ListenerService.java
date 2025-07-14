package com.app_fitness.training_card_service.service;

import com.app_fitness.training_card_service.model.TrainingCard;
import com.app_fitness.training_card_service.model.TrainingPreferences;
import com.app_fitness.training_card_service.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListenerService {

    private final CardService cardService;
    private final CardRepository cardRepository;

    @RabbitListener(queues = "ai-activity.queue")
    public void processActivity(TrainingPreferences preferences) throws JsonProcessingException {
        log.info("[LISTENER] Attività ricevuta");
        TrainingCard trainingCard = cardService.generaCard(preferences);
        cardRepository.save(trainingCard);
    }
}
