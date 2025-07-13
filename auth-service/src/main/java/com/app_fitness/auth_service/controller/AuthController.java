package com.app_fitness.auth_service.controller;

import com.app_fitness.auth_service.dto.LoginRequestDto;
import com.app_fitness.auth_service.dto.LoginResponseDto;
import com.app_fitness.auth_service.dto.RegisterRequestDto;
import com.app_fitness.auth_service.dto.RegisterResponseDto;
import com.app_fitness.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<Boolean> validUser(@PathVariable String id){
        return ResponseEntity.ok(authService.validate(id));
    }
}
