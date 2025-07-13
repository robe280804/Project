package com.app_fitness.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {

    private String name;
    private String surname;
    private String email;
}
