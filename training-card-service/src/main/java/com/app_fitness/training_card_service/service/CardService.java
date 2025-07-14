package com.app_fitness.training_card_service.service;

import com.app_fitness.training_card_service.model.DailyCard;
import com.app_fitness.training_card_service.model.Exercise;
import com.app_fitness.training_card_service.model.TrainingCard;
import com.app_fitness.training_card_service.model.TrainingPreferences;
import com.app_fitness.training_card_service.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final GeminiService geminiService;
    private final UserValidation userValidation;
    private final CardRepository cardRepository;

    public TrainingCard generaCard(TrainingPreferences preferences) throws JsonProcessingException {
        String prompt = createPrompt(preferences);
        String response = geminiService.getAnswer(prompt);
        log.info("[GEMINI] Risposta da gemini {}", response);
        return convertAiResponse(preferences, response);
    }

    private TrainingCard convertAiResponse(TrainingPreferences preferences, String response) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode textNode = rootNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            if (textNode.isMissingNode()) {
                throw new RuntimeException("Campo 'text' mancante nella risposta");
            }

            String cleanedJson = textNode.asText()
                    .replaceAll("(?s)```json\\s*", "")
                    .replaceAll("```", "")
                    .trim();

            JsonNode internalRoot = mapper.readTree(cleanedJson);

            JsonNode trainingCardNode = internalRoot.path("trainingCard").get(0);
            JsonNode dailyCardNode = trainingCardNode.get("dailyCard");

            TrainingCard trainingCard = new TrainingCard();
            List<DailyCard> dailyCards = new ArrayList<>();

            for (JsonNode dayNode : dailyCardNode){
                DailyCard dailyCard = new DailyCard();
                dailyCard.setDay(dayNode.get("day").asText());
                dailyCard.setTrainingDuration(dayNode.get("trainingDuration").asText());

                List<Exercise> exercises = new ArrayList<>();
                for (JsonNode exerciseNode : dayNode.get("exercises")){
                    Exercise exercise = new Exercise();
                    exercise.setName(exerciseNode.get("name").asText());
                    exercise.setSeries(exerciseNode.get("series").asText());
                    exercise.setRepetition(exerciseNode.get("repetitions").asText());
                    exercise.setRest(exerciseNode.get("rest").asText());

                    exercises.add(exercise);
                }
                dailyCard.setExerciseList(exercises);
                dailyCards.add(dailyCard);
            }

            trainingCard.setCard(dailyCards);
            trainingCard.setId(preferences.getId());
            return trainingCard;


        } catch (Exception ex){
            throw new RuntimeException("Errore ", ex);
        }
    }


    public String createPrompt(TrainingPreferences preferences) {
        return String.format("""
                        Analizza queste preferenze utente e genera un piano di allenamento settimanale nel formato JSON indicato.
                        Il piano deve contenere esattamente %d dailyCard, una per ogni giorno di allenamento, con dettagli su esercizi, durata e riposo.
                        
                        Il formato JSON richiesto è:
                        
                        {
                          "trainingCard": [
                            {
                              "dailyCard": [
                                {
                                  "day": "Nome del giorno (es. Lunedì)",
                                  "trainingDuration": "Durata allenamento in minuti",
                                  "exercises": [
                                    {
                                      "name": "Nome esercizio",
                                      "series": "Numero di serie",
                                      "repetitions": "Numero di ripetizioni",
                                      "rest": "Recupero tra serie in secondi"
                                    }
                                  ]
                                },
                                ... // altre dailyCard fino a %d elementi
                              ]
                            }
                          ]
                        }
                        
                        Preferenze utente:
                        - Goals: %s
                        - Session duration: %d minuti
                        - Days per week: %d
                        - Free days: %s
                        - Training types: %s
                        - Level: %s
                        - Additional metrics: %s
                        
                        Organizza i giorni di allenamento evitando i giorni liberi indicati e distribuisci gli esercizi coerenti con i goal e il livello.
                        """,
                preferences.getDaysPerWeek(),
                preferences.getDaysPerWeek(),
                preferences.getGoals(),
                preferences.getSessionDuration(),
                preferences.getDaysPerWeek(),
                preferences.getFreeDays(),
                preferences.getType(),
                preferences.getLevel(),
                preferences.getAdditionalMetrics()
        );
    }

    @Transactional
    public List<TrainingCard> getUserCard(String userId) {
        if (!userValidation.userValidation(userId)){
            throw new RuntimeException("User non autorizzato" + userId);
        }
        return cardRepository.findAllByUserId(userId);
    }
}

