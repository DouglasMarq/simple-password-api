package com.douglasmarq.password.simple_password.domain.dto;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordRequest {
    @NotNull private String password;
}
