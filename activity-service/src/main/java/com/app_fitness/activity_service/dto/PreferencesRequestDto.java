package com.app_fitness.activity_service.dto;

import com.app_fitness.activity_service.model.Goals;
import com.app_fitness.activity_service.model.Level;
import com.app_fitness.activity_service.model.TrainingType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesRequestDto {

    @NotBlank(message = "L'ID dell'utente è obbligatorio.")
    private String userId;

    @NotEmpty(message = "Devi selezionare almeno un obiettivo.")
    @Size(max = 3, message = "Puoi selezionare al massimo 3 tipi di obbiettivi.")
    private List<@NotNull(message = "Gli obiettivi non possono contenere valori nulli.") Goals> goals;

    @NotNull(message = "La durata della sessione è obbligatoria.")
    @Min(value = 10, message = "La sessione deve durare almeno 10 minuti.")
    @Max(value = 180, message = "La durata della sessione non può superare i 180 minuti.")
    private Integer sessionDuration;

    @NotNull(message = "Il numero di giorni di allenamento a settimana è obbligatorio.")
    @Min(value = 1, message = "Devi allenarti almeno 1 giorno a settimana.")
    @Max(value = 7, message = "Non puoi allenarti più di 7 giorni a settimana.")
    private Integer daysPerWeek;

    @NotNull(message = "L'elenco dei giorni liberi non può essere nullo.")
    @Size(max = 6, message = "Devi lasciare almeno un giorno disponibile per l'allenamento.")
    private List<@NotBlank(message = "I giorni liberi non possono essere vuoti.") String> freeDays;

    @NotEmpty(message = "Devi selezionare almeno un tipo di allenamento.")
    @Size(max = 2, message = "Puoi selezionare al massimo 2 tipi di allenamento.")
    private List<@NotNull(message = "Il tipo di allenamento non può essere nullo.") TrainingType> type;


    @NotNull(message = "Il livello è obbligatorio.")
    private Level level;
    private Map<String, Object> additionalMetrics;
}
