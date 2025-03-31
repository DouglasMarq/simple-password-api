package com.douglasmarq.password.simple_password.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PasswordResponse {
    private boolean valid;
}
