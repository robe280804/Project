package com.app_fitness.training_card_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("training-card")
@Data
public class TrainingCard {

    @Id
    private String id;
    private String userId;
    private List<DailyCard> card;
}




