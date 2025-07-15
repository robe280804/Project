package com.app_fitness.training_card_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.Map;

import static org.apache.hc.core5.http.message.MessageSupport.header;

/* Body che accetta gemini per la richiesta
{  "contents": [{
        "parts": [{
            "text": "Explain how AI works in a few words"
          }]
      }]
}
 */

@Service
@Slf4j
public class GeminiService {

    @Value("${GEMINI_API_URL}")
    private String geminiUrl;

    @Value(("${GEMINI_API_KEY}"))
    private String geminiKey;

    private final WebClient webClient;

    public GeminiService(@Qualifier("standardBuilder") WebClient.Builder standardClientBuilder) {
        this.webClient = standardClientBuilder.build();
    }

    public String getAnswer(String question) {
        Map<String, Object> request = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                });
        try {
            return webClient.post()
                    .uri(geminiUrl.trim() + geminiKey.trim())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new RuntimeException("Errore API: " + errorBody)))
                    )
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            log.error("Errore chiamata Gemini", e);
            return null;  // o gestione alternativa
        }
    }
/*
        return webClient.post()
                .uri(geminiUrl.trim() + geminiKey.trim())
                .header("Content-Type", "application/json")
                .bodyValue(question)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Errore API: " + errorBody)))
                )
                .bodyToMono(String.class)
                .block();
    }
*/

}