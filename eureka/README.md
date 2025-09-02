# Eureka Server

## Descrizione
Il server Eureka è il servizio di discovery e registry per l'architettura di microservizi AI Fitness. Gestisce la registrazione, scoperta e monitoraggio di tutti i microservizi dell'applicazione, fornendo un meccanismo di service discovery automatico.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Cloud Netflix Eureka Server** - Per il service discovery
- **Spring Cloud Config Client** - Per la configurazione centralizzata

## Struttura del Progetto

```
eureka/
├── src/main/java/com/app_fitness/eureka/
│   └── EurekaApplication.java               # Classe principale dell'applicazione
├── src/main/resources/
│   └── application.yml                      # Configurazione dell'applicazione
└── Dockerfile                              # Configurazione Docker
```

## Funzionalità Principali

### 1. Service Registry
- Registrazione automatica dei microservizi
- Mantenimento di un registro centralizzato dei servizi
- Gestione dello stato di salute dei servizi

### 2. Service Discovery
- Scoperta automatica dei servizi disponibili
- Risoluzione dinamica degli endpoint

### 3. Health Monitoring
- Monitoraggio continuo dello stato dei servizi
- Rimozione automatica dei servizi non disponibili

### 4. Load Balancing
- Distribuzione automatica del carico


## Servizi Registrati

Il server Eureka gestisce la registrazione dei seguenti microservizi:

- **auth-service** - Porta 8081
- **activity-service** - Porta 8082
- **training-card-service** - Porta 8083
- **gateway-service** - Porta 8080
- **config-service** - Porta 8888

## Dashboard Eureka

### Accesso alla Dashboard
- **URL:** http://localhost:8761
- **Interfaccia Web:** Dashboard per monitorare i servizi registrati


## Avvio del Servizio

### Con Docker
```bash
docker-compose up eureka
```

### Localmente
```bash
cd eureka
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8761**.

## Dipendenze
- **Config Service** - Per la configurazione centralizzata

## Note di Sviluppo
- Eureka deve essere avviato per primo
- Tutti i microservizi dipendono da Eureka


