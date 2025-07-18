package com.app_fitness.activity_service.controller;

import com.app_fitness.activity_service.dto.PreferencesRequestDto;
import com.app_fitness.activity_service.dto.PreferencesResponseDto;
import com.app_fitness.activity_service.service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/preferences")
@RequiredArgsConstructor
public class PreferencesController {

    private final PreferencesService preferencesService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/register")
    public ResponseEntity<PreferencesResponseDto> insertPreferences(@RequestBody PreferencesRequestDto request){
        return ResponseEntity.ok(preferencesService.insert(request));
    }

    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    @GetMapping("/{userId}")
    public ResponseEntity<List<PreferencesResponseDto>> getUserPreferences(@PathVariable String userId){
        return ResponseEntity.ok(preferencesService.getUserPreferences(userId));
    }
}
