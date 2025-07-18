package com.app_fitness.activity_service.service;

import com.app_fitness.activity_service.dto.PreferencesRequestDto;
import com.app_fitness.activity_service.dto.PreferencesResponseDto;
import com.app_fitness.activity_service.model.TrainingPreferences;
import com.app_fitness.activity_service.repository.PreferencesRepository;
import com.app_fitness.common_files.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PreferencesService {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final UserValidation userValidation;  //common-files
    private final PreferencesRepository preferencesRepository ;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public PreferencesResponseDto insert(PreferencesRequestDto request) {
        log.info("[REGISTER PREFERENCES] Register preferences for user {}", request.getUserId());

        if (!userValidation.userValidation(request.getUserId())){
            throw new RuntimeException("User non autorizzatio");
        }

        TrainingPreferences preferences = TrainingPreferences.builder()
                .userId(request.getUserId())
                .goals(request.getGoals())
                .sessionDuration(request.getSessionDuration())
                .daysPerWeek(request.getDaysPerWeek())
                .freeDays(request.getFreeDays())
                .type(request.getType())
                .level(request.getLevel())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        TrainingPreferences savedPreferences = preferencesRepository.save(preferences);

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedPreferences);
            log.info("[RABBITMQ] Attività inviata a Rabbit MQ {}", savedPreferences);
        } catch (Exception ex) {
            log.error("Errore con il passaggio a RabbitMQ : " + ex);
        }
        return PreferencesResponseDto.builder()
                .goals(savedPreferences.getGoals())
                .sessionDuration(savedPreferences.getSessionDuration())
                .daysPerWeek(savedPreferences.getDaysPerWeek())
                .freeDays(savedPreferences.getFreeDays())
                .type(savedPreferences.getType())
                .level(savedPreferences.getLevel())
                .additionalMetrics(savedPreferences.getAdditionalMetrics())
                .createdAt(savedPreferences.getCreatedAt())
                .build();


    }

    @Transactional
    public List<PreferencesResponseDto> getUserPreferences(String userId) {
        log.info("[GET PREFERENCES] Get preferences for user {}", userId);

        if (!userValidation.userValidation(userId)){
            throw new RuntimeException("User non autorizzatio");
        }

        return preferencesRepository.findAllByUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private PreferencesResponseDto mapToResponseDto(TrainingPreferences preference) {
        return PreferencesResponseDto.builder()
                .goals(preference.getGoals())
                .sessionDuration(preference.getSessionDuration())
                .daysPerWeek(preference.getDaysPerWeek())
                .freeDays(preference.getFreeDays())
                .type(preference.getType())
                .level(preference.getLevel())
                .additionalMetrics(preference.getAdditionalMetrics())
                .createdAt(preference.getCreatedAt())
                .build();
    }
}
