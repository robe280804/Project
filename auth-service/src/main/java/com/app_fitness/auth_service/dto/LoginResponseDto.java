package com.app_fitness.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private String email;
    private String token;
}
