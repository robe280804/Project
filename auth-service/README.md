# Auth Service

## Descrizione
Il servizio di autenticazione gestisce la registrazione, il login e la validazione degli utenti dell'applicazione AI Fitness. Utilizza JWT (JSON Web Token) per l'autenticazione e autorizzazione.

## Tecnologie Utilizzate
- **Spring Boot 3.x**
- **Spring Security** - Per l'autenticazione e autorizzazione
- **JWT** - Per la gestione dei token
- **MySQL** - Database relazionale per la persistenza degli utenti
- **Spring Data JPA** - Per l'accesso ai dati
- **Lombok** - Per ridurre il codice boilerplate

## Struttura del Progetto

```
auth-service/
├── src/main/java/com/app_fitness/auth_service/
│   ├── AuthServiceApplication.java          # Classe principale dell'applicazione
│   ├── controller/
│   │   └── AuthController.java              # Controller REST per l'autenticazione
│   ├── dto/
│   │   ├── LoginRequestDto.java             # DTO per le richieste di login
│   │   ├── LoginResponseDto.java            # DTO per le risposte di login
│   │   ├── RegisterRequestDto.java          # DTO per le richieste di registrazione
│   │   └── RegisterResponseDto.java         # DTO per le risposte di registrazione
│   ├── model/
│   │   ├── Role.java                        # Entità per i ruoli utente
│   │   └── User.java                        # Entità utente
│   ├── repository/
│   │   └── UserRepository.java              # Repository per l'accesso ai dati utente
│   ├── security/
│   │   ├── ConfigSecurity.java              # Configurazione di sicurezza
│   │   └── user/
│   │       ├── CustomUserDetails.java       # Implementazione UserDetails
│   │       └── CustomUserDetailsService.java # Servizio per il caricamento utenti
│   └── service/
│       └── AuthService.java                 # Logica di business per l'autenticazione
├── src/main/resources/
│   └── application.yml                      # Configurazione dell'applicazione
└── Dockerfile                              # Configurazione Docker
```

## API Endpoints

### POST `/api/auth/register`
Registra un nuovo utente nel sistema.


### POST `/api/auth/login`
Autentica un utente esistente.


### GET `/api/auth/validate/{id}`
Valida se un utente esiste nel sistema.


## Configurazione

### Variabili d'Ambiente
- `SPRING_DATASOURCE_URL` - URL del database MySQL
- `SPRING_DATASOURCE_USERNAME` - Username del database
- `SPRING_DATASOURCE_PASSWORD` - Password del database
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - URL del server Eureka

### Database
Il servizio utilizza MySQL per la persistenza dei dati utente. Le tabelle principali sono:
- `users` - Informazioni degli utenti
- `roles` - Ruoli disponibili nel sistema

## Sicurezza
- Utilizza Spring Security per l'autenticazione
- Implementa JWT per la gestione dei token
- Password crittografate con BCrypt
- Configurazione CORS per le richieste cross-origin

## Avvio del Servizio

### Con Docker
```bash
docker-compose up auth-service
```

### Localmente
```bash
cd auth-service
./mvnw spring-boot:run
```

Il servizio sarà disponibile sulla porta **8081**.

## Dipendenze
- **Eureka Server** - Per la registrazione del servizio
- **Config Service** - Per la gestione centralizzata della configurazione
- **MySQL** - Database per la persistenza

## Note di Sviluppo
- Il servizio è configurato per integrarsi con il sistema di microservizi
- Utilizza il common-files module per funzionalità condivise
- Supporta la validazione degli utenti per altri servizi
