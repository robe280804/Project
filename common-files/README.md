# Common Files

## Descrizione
Il modulo Common Files contiene le classi e le configurazioni condivise tra tutti i microservizi dell'applicazione AI Fitness. Fornisce funzionalità comuni per la sicurezza, la validazione e la comunicazione tra servizi.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Security** - Per la sicurezza condivisa
- **Spring WebFlux** - Per la comunicazione HTTP reattiva
- **JWT** - Per la gestione dei token
- **Lombok** - Per ridurre il codice boilerplate

## Struttura del Progetto

```
common-files/
├── src/main/java/com/app_fitness/common_files/
│   ├── config/
│   │   └── WebClientConfig.java             # Configurazione WebClient
│   ├── security/
│   │   ├── filter/
│   │   │   └── HeaderAuthFilter.java        # Filtro per l'autenticazione
│   │   └── jwt/
│   │       └── JwtService.java              # Servizio JWT condiviso
│   └── validation/
│       └── UserValidation.java              # Validazioni utente
├── src/main/resources/
│   └── META-INF/
│       └── maven/
│           └── archetype.xml                # Configurazione Maven
└── pom.xml                                 # Configurazione Maven
```

-------------------

## Componenti Principali

### 1. JwtService
Servizio condiviso per la gestione dei token JWT.


### 2. WebClientConfig
Configurazione per la comunicazione HTTP tra microservizi.


### 3. HeaderAuthFilter
Filtro per l'autenticazione tramite header HTTP.


### 4. UserValidation
Classe per le validazioni comuni degli utenti.

-------------------

## Funzionalità di Sicurezza

### 1. Gestione JWT
- Generazione di token sicuri
- Validazione dei token
- Estrazione delle informazioni utente
- Gestione della scadenza

### 2. Filtri di Autenticazione
- Validazione degli header di autorizzazione
- Estrazione delle informazioni utente
- Controllo dei ruoli e permessi

### 3. Validazioni
- Validazione degli ID utente
- Validazione delle email
- Validazione delle password
- Controlli di sicurezza comuni

-----------------

## Note di Sviluppo
- Il modulo è condiviso tra tutti i microservizi
- Mantiene la coerenza della sicurezza
- Facilita la manutenzione del codice
- Riduce la duplicazione delle funzionalità
- Fornisce un'interfaccia standardizzata
- Supporta la scalabilità e l'estensibilità
