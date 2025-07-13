package com.app_fitness.auth_service.service;

import com.app_fitness.auth_service.dto.LoginRequestDto;
import com.app_fitness.auth_service.dto.LoginResponseDto;
import com.app_fitness.auth_service.dto.RegisterRequestDto;
import com.app_fitness.auth_service.dto.RegisterResponseDto;
import com.app_fitness.auth_service.model.Role;
import com.app_fitness.auth_service.model.User;
import com.app_fitness.auth_service.repository.UserRepository;
import com.app_fitness.auth_service.security.JwtService;
import com.app_fitness.auth_service.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcrypt;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        log.info("[REGISTER] Richiesta da email {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())){
            log.warn("[REGISTER] Utente già registrato con email {}", request.getEmail());
            throw new RuntimeException("Email già in uso: " + request.getEmail());
        }
        String passwordHash = bcrypt.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordHash)
                .role(Role.USER)
                .build();

        User userSaved = userRepository.save(user);
        log.info("[REGISTER] Registrazione completata per {}", userSaved.getEmail());

        return RegisterResponseDto.builder()
                .name(user.getName())
                .surname(user.getSurname()) 
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        log.info("[LOGIN] Richiesta da email {}", request.getEmail());
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getAuthorities());

            log.info("[LOGIN] Login andato a buon fine per {}", request.getEmail());
            return LoginResponseDto.builder()
                    .email(userDetails.getUsername())
                    .token(token)
                    .build();

        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Email o password errati " + ex);
        } catch (DisabledException ex){
            throw new RuntimeException("Utente disabilitato " + ex);
        } catch (Exception ex){
            throw new RuntimeException("Errore " + ex);
        }

    }
}
