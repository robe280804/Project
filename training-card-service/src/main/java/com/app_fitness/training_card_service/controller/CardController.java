package com.app_fitness.training_card_service.controller;

import com.app_fitness.training_card_service.model.TrainingCard;
import com.app_fitness.training_card_service.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingCard>> getUserCard(@PathVariable String userId){
        return ResponseEntity.ok(cardService.getUserCard(userId));
    }

}
