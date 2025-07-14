package com.app_fitness.training_card_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPreferences {


    private String id;
    private String userId;
    private List<Goals> goals;
    private Integer sessionDuration;
    private Integer daysPerWeek;
    private List<String> freeDays;  //giorni disponibili
    private List<TrainingType> type;  //corsa, sala pesi ...
    private Level level;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
