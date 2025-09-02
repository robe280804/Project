# Training Card Service

## Descrizione
Il servizio delle schede di allenamento è il cuore dell'applicazione AI Fitness. Genera schede di allenamento personalizzate utilizzando l'API di Google Gemini AI, basandosi sulle preferenze degli utenti. Gestisce anche la comunicazione asincrona tramite RabbitMQ per ricevere aggiornamenti sulle preferenze.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Security** - Per l'autenticazione e autorizzazione
- **MongoDB** - Database NoSQL per la persistenza delle schede
- **Spring Data MongoDB** - Per l'accesso ai dati
- **RabbitMQ** - Per la messaggistica asincrona
- **Google Gemini AI API** - Per la generazione intelligente delle schede
- **WebClient** - Per le chiamate HTTP asincrone
- **Lombok** - Per ridurre il codice boilerplate

## Struttura del Progetto

```
training-card-service/
├── src/main/java/com/app_fitness/training_card_service/
│   ├── TrainingCardServiceApplication.java  # Classe principale dell'applicazione
│   ├── config/
│   │   ├── MongoConfig.java                 # Configurazione MongoDB
│   │   ├── RabbitMqConfig.java              # Configurazione RabbitMQ
│   │   └── SecurityConfig.java              # Configurazione di sicurezza
│   ├── controller/
│   │   └── CardController.java              # Controller REST per le schede
│   ├── model/
│   │   ├── DailyCard.java                   # Entità per le schede giornaliere
│   │   ├── Exercise.java                    # Entità per gli esercizi
│   │   ├── Goals.java                       # Enum per gli obiettivi
│   │   ├── Level.java                       # Enum per i livelli
│   │   ├── TrainingCard.java                # Entità principale per le schede
│   │   ├── TrainingPreferences.java         # Entità per le preferenze
│   │   └── TrainingType.java                # Enum per i tipi di allenamento
│   ├── repository/
│   │   └── CardRepository.java              # Repository per l'accesso ai dati
│   └── service/
│       ├── CardService.java                 # Logica di business per le schede
│       ├── GeminiService.java               # Servizio per l'integrazione con Gemini AI
│       └── ListenerService.java             # Servizio per l'ascolto di RabbitMQ
├── src/main/resources/
│   └── application.yml                      # Configurazione dell'applicazione
└── Dockerfile                              # Configurazione Docker
```

## API Endpoints

### GET `/api/user/card/{userId}`
Recupera tutte le schede di allenamento di un utente specifico.


## Integrazione con Gemini AI

Il servizio utilizza l'API di Google Gemini per generare schede di allenamento personalizzate basate su:

- **Preferenze utente** ricevute tramite RabbitMQ
- **Obiettivi di allenamento** (perdita peso, aumento massa, resistenza)
- **Livello di esperienza** (principiante, intermedio, avanzato)
- **Tipi di allenamento preferiti** (cardio, forza, flessibilità)
- **Durata e frequenza** desiderate

### Configurazione Gemini AI
```yaml
gemini:
  api:
    url: "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="
    key: "${GEMINI_API_KEY}"
```

## Messaggistica RabbitMQ

Il servizio ascolta i messaggi RabbitMQ per:
- **Aggiornamenti delle preferenze** utente
- **Generazione automatica** di nuove schede

### Configurazione RabbitMQ
```yaml
spring:
  rabbitmq:
    host: "${SPRING_RABBITMQ_HOST}"
    username: "${SPRING_RABBITMQ_USERNAME}"
    password: "${SPRING_RABBITMQ_PASSWORD}"
```

## Configurazione

### Variabili d'Ambiente
- `SPRING_DATA_MONGODB_URI` - URI di connessione MongoDB
- `SPRING_RABBITMQ_HOST` - Host RabbitMQ
- `SPRING_RABBITMQ_USERNAME` - Username RabbitMQ
- `SPRING_RABBITMQ_PASSWORD` - Password RabbitMQ
- `GEMINI_API_URL` - URL dell'API Gemini
- `GEMINI_API_KEY` - Chiave API per Gemini
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - URL del server Eureka

### Database
Il servizio utilizza MongoDB per la persistenza delle schede di allenamento. Le collezioni principali sono:
- `training_cards` - Schede di allenamento generate


## Sicurezza
- Utilizza Spring Security con JWT per l'autenticazione
- Endpoint protetti con `@PreAuthorize`
- Validazione dell'utente tramite token JWT
- Controllo di accesso basato sui ruoli

## Avvio del Servizio

### Con Docker
```bash
docker-compose up training-card-service
```

### Localmente
```bash
cd training-card-service
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8083**.

## Dipendenze
- **Eureka Server** - Per la registrazione del servizio
- **Config Service** - Per la gestione centralizzata della configurazione
- **MongoDB** - Database per la persistenza
- **RabbitMQ** - Per la messaggistica asincrona
- **Auth Service** - Per la validazione dei token JWT
- **Activity Service** - Per ricevere aggiornamenti delle preferenze
- **Google Gemini AI** - Per la generazione intelligente delle schede

## Note di Sviluppo
- Integrazione con AI per personalizzazione
- Gestione asincrona delle richieste tramite RabbitMQ
- Supporto per generazione dinamica di schede
- Logging dettagliato per il monitoraggio delle chiamate AI
