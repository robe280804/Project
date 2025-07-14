package com.app_fitness.training_card_service.model;


import lombok.Data;

import java.util.List;

@Data
public class DailyCard {

    private String day;
    private List<Exercise> exerciseList;
    private String trainingDuration;
}
