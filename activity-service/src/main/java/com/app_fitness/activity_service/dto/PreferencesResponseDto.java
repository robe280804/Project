package com.app_fitness.activity_service.dto;

import com.app_fitness.activity_service.model.Goals;
import com.app_fitness.activity_service.model.Level;
import com.app_fitness.activity_service.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesResponseDto {

    private List<Goals> goals;
    private Integer sessionDuration;
    private Integer daysPerWeek;
    private List<String> freeDays;
    private List<TrainingType> type;
    private Level level;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
}
