package com.app_fitness.activity_service.model;

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

@Document("training-preferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPreferences {

    @Id
    private String id;
    @Field("user_id")
    private String userId;
    private List<Goals> goals;
    @Field("session_duration")
    private Integer sessionDuration;
    @Field("days_per_week")
    private Integer daysPerWeek;
    @Field("free_days")
    private List<String> freeDays;  //giorni disponibili
    private List<TrainingType> type;  //corsa, sala pesi ...
    private Level level;
    @Field("additional_metrics")
    private Map<String, Object> additionalMetrics;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
