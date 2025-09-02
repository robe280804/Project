# Gateway Service

## Descrizione
Il servizio Gateway funge da punto di ingresso unificato per tutti i microservizi dell'applicazione AI Fitness. Implementa un API Gateway che gestisce il routing delle richieste, l'autenticazione JWT, il load balancing e la sicurezza centralizzata.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Cloud Gateway** - Per il routing e il load balancing
- **Spring Security** - Per l'autenticazione e autorizzazione
- **JWT** - Per la validazione dei token
- **Eureka Client** - Per la scoperta dei servizi
- **WebFlux** - Per la programmazione reattiva

## Struttura del Progetto

```
gateway-service/
├── src/main/java/com/app_fitness/getaway_service/
│   ├── GetawayServiceApplication.java        # Classe principale dell'applicazione
│   ├── JwtService.java                      # Servizio per la gestione JWT
│   ├── RequestFilter.java                   # Filtro per le richieste
│   ├── SecurityConfig.java                  # Configurazione di sicurezza
│   └── TokenFilter.java                     # Filtro per la validazione dei token
├── src/main/resources/
│   └── application.yml                      # Configurazione dell'applicazione
└── Dockerfile                              # Configurazione Docker
```

## Funzionalità Principali

### 1. Routing delle Richieste
Il Gateway instrada le richieste ai microservizi appropriati:

- **Auth Service** - `/api/auth/**` → `auth-service:8081`
- **Activity Service** - `/api/user/preferences/**` → `activity-service:8082`
- **Training Card Service** - `/api/user/card/**` → `training-card-service:8083`

### 2. Autenticazione JWT
- Validazione dei token JWT per tutte le richieste protette
- Estrazione delle informazioni utente dal token
- Controllo delle autorizzazioni basato sui ruoli

### 3. Load Balancing
- Distribuzione automatica del carico tra istanze multiple
- Integrazione con Eureka per la scoperta dei servizi
- Gestione automatica dei failover

### 4. Sicurezza Centralizzata
- Filtri di sicurezza applicati a tutte le richieste
- Gestione CORS centralizzata


## Configurazione

### Variabili d'Ambiente
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - URL del server Eureka
- `SPRING_CONFIG_IMPORT` - URL del config service
- `JWT_SECRET` - Chiave segreta per la validazione JWT


## Avvio del Servizio

### Con Docker
```bash
docker-compose up gateway-service
```

### Localmente
```bash
cd gateway-service
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8080**.

## Dipendenze
- **Eureka Server** - Per la scoperta dei servizi
- **Config Service** - Per la gestione centralizzata della configurazione
- **Auth Service** - Per la validazione dei token JWT
- **Activity Service** - Per il routing delle preferenze
- **Training Card Service** - Per il routing delle schede


## Note di Sviluppo
- Il Gateway è il punto di ingresso principale dell'applicazione
- Gestisce automaticamente il routing e il load balancing
- Implementa sicurezza centralizzata per tutti i microservizi

