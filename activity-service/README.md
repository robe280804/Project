# Activity Service

## Descrizione
Il servizio delle attività gestisce le preferenze di allenamento degli utenti, inclusi obiettivi, livelli di esperienza e tipi di allenamento preferiti. Utilizza MongoDB per la persistenza dei dati e RabbitMQ per la comunicazione asincrona.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Security** - Per l'autenticazione e autorizzazione
- **MongoDB** - Database NoSQL per la persistenza delle preferenze
- **Spring Data MongoDB** - Per l'accesso ai dati
- **RabbitMQ** - Per la messaggistica asincrona
- **Lombok** - Per ridurre il codice boilerplate

## Struttura del Progetto

```
activity-service/
├── src/main/java/com/app_fitness/activity_service/
│   ├── ActivityServiceApplication.java      # Classe principale dell'applicazione
│   ├── config/
│   │   ├── MongoConfig.java                 # Configurazione MongoDB
│   │   ├── RabbitMqConfig.java              # Configurazione RabbitMQ
│   │   └── SecurityConfig.java              # Configurazione di sicurezza
│   ├── controller/
│   │   └── PreferencesController.java       # Controller REST per le preferenze
│   ├── dto/
│   │   ├── PreferencesRequestDto.java       # DTO per le richieste di preferenze
│   │   └── PreferencesResponseDto.java      # DTO per le risposte di preferenze
│   ├── model/
│   │   ├── Goals.java                       # Enum per gli obiettivi di allenamento
│   │   ├── Level.java                       # Enum per i livelli di esperienza
│   │   ├── TrainingPreferences.java         # Entità per le preferenze di allenamento
│   │   └── TrainingType.java                # Enum per i tipi di allenamento
│   ├── repository/
│   │   └── PreferencesRepository.java       # Repository per l'accesso ai dati
│   └── service/
│       └── PreferencesService.java          # Logica di business per le preferenze
├── src/main/resources/
│   └── application.yml                      # Configurazione dell'applicazione
└── Dockerfile                              # Configurazione Docker
```

### Enums Disponibili

#### Goals (Obiettivi)
- `WEIGHT_LOSS` - Perdita di peso
- `MUSCLE_GAIN` - Aumento della massa muscolare
- `ENDURANCE` - Miglioramento della resistenza
- `FLEXIBILITY` - Miglioramento della flessibilità
- `GENERAL_FITNESS` - Fitness generale

#### Level (Livelli)
- `BEGINNER` - Principiante
- `INTERMEDIATE` - Intermedio
- `ADVANCED` - Avanzato

#### TrainingType (Tipi di Allenamento)
- `CARDIO` - Allenamento cardiovascolare
- `STRENGTH` - Allenamento di forza
- `FLEXIBILITY` - Allenamento di flessibilità
- `HIIT` - High Intensity Interval Training
- `YOGA` - Yoga
- `PILATES` - Pilates

## API Endpoints

### POST `/api/user/preferences/register`
Registra le preferenze di allenamento per un utente.


### GET `/api/user/preferences/{userId}`
Recupera le preferenze di allenamento di un utente specifico.


## Configurazione

### Variabili d'Ambiente
- `SPRING_DATA_MONGODB_URI` - URI di connessione MongoDB
- `SPRING_RABBITMQ_HOST` - Host RabbitMQ
- `SPRING_RABBITMQ_USERNAME` - Username RabbitMQ
- `SPRING_RABBITMQ_PASSWORD` - Password RabbitMQ
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - URL del server Eureka

### Database
Il servizio utilizza MongoDB per la persistenza delle preferenze utente. La collezione principale è:
- `training_preferences` - Preferenze di allenamento degli utenti

## Sicurezza
- Utilizza Spring Security con JWT per l'autenticazione
- Endpoint protetti con `@PreAuthorize`
- Validazione dell'utente tramite token JWT
- Controllo di accesso basato sui ruoli

## Messaggistica
- Utilizza RabbitMQ per la comunicazione asincrona
- Configurazione per lo scambio di messaggi tra servizi


## Avvio del Servizio

### Con Docker
```bash
docker-compose up activity-service
```

### Localmente
```bash
cd activity-service
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8082**.

## Dipendenze
- **Eureka Server** - Per la registrazione del servizio
- **Config Service** - Per la gestione centralizzata della configurazione
- **MongoDB** - Database per la persistenza
- **RabbitMQ** - Per la messaggistica asincrona
- **Auth Service** - Per la validazione dei token JWT

## Note di Sviluppo
- Il servizio è progettato per essere scalabile e resiliente
- Utilizza MongoDB per gestire dati non strutturati e flessibili
- Integrazione con RabbitMQ per comunicazione asincrona
- Supporta multiple preferenze per utente
