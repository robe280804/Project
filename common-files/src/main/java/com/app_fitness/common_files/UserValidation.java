package com.app_fitness.common_files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class UserValidation {

    private final WebClient webClient;

    public UserValidation(@Qualifier("authServiceWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Boolean userValidation(String id){
        log.info("[VALIDATION] Validazione per user {}", id);

        try {
            return webClient.get()
                    .uri("/api/auth/validate/{id}", id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException ex){
            throw new RuntimeException("Errore " + ex);
        }
    }
}
