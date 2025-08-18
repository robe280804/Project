
Applicazione Fitness Ai

Piattaforma modulare basata su microservizi pensata per aiutare le persone in difficolta con la creazione di schede
d'allenamento, basate sulle loro preferenze.

## 🏗️ Architettura a Microservizi

Il sistema è composto dai seguenti servizi:

- `gateway-service`: gestisce il routing delle richieste esterne
- `auth-service`: gestione utenti e autenticazione JWT
- `activity-service`: gestione le preferenze dell' utente per la creazione della scheda
- `training-card-service`: generazione delle schede d'allenamento attraverso Gemini API

Servizi ausiliari:
- `config-server`: centralizzazione delle configurazioni
- `eureka-server`: service discovery

Ogni microservizio è autonomo e comunicano via REST o Eventi (RabbitMQ).

## 🛠️ Stack Tecnologico

- Java 19
- Spring Boot 3.x
- Spring Cloud (Config, Gateway, Eureka)
- MySQL + MongoDB
- RabbitMQ
- Docker 
- Gemini API

## DOCKER 

▶️ Avvio con Docker Compose

Dalla root del progetto, in Windows PowerShell:

docker compose build
docker compose up -d