# Config Service

## Descrizione
Il servizio di configurazione centralizzata gestisce tutte le configurazioni dei microservizi dell'applicazione AI Fitness. Utilizza Spring Cloud Config per fornire configurazioni centralizzate, versionate e gestibili dinamicamente per tutti i servizi dell'ecosistema.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Cloud Config Server** - Per la gestione centralizzata delle configurazioni
- **Spring Cloud Config Client** - Per il consumo delle configurazioni
- **Git/Native** - Per il repository delle configurazioni
- **Spring Profiles** - Per la gestione degli ambienti

## Struttura del Progetto

```
config-service/
├── src/main/java/com/app_fitness/
│   └── ConfigServiceApplication.java         # Classe principale dell'applicazione
├── src/main/resources/
│   ├── application.yml                      # Configurazione principale
│   └── config/                              # Directory delle configurazioni
│       ├── activity-service.yml             # Configurazione per activity-service
│       ├── auth-service.yml                 # Configurazione per auth-service
│       ├── gateway-service.yml              # Configurazione per gateway-service
│       └── training-card-service.yml        # Configurazione per training-card-service
└── Dockerfile                              # Configurazione Docker
```


## Funzionalità Principali

### 1. Configurazione Centralizzata
- Tutte le configurazioni dei microservizi in un unico posto
- Gestione versionata delle configurazioni

### 2. Integrazione con Eureka
- Configurazione automatica della scoperta dei servizi
- Gestione centralizzata degli URL dei servizi


## Avvio del Servizio

### Con Docker
```bash
docker-compose up config-service
```

### Localmente
```bash
cd config-service
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8888**.

## Dipendenze
- **Nessuna dipendenza esterna** - Il config service è indipendente
- **File system locale** - Per il repository delle configurazioni
- **Spring Cloud Config** - Per la gestione delle configurazioni

## Note di Sviluppo
- Il config service deve essere avviato per primo dopo l'eureka server
- Tutti i microservizi dipendono da questo servizio
- Gestione centralizzata delle credenziali sensibili
