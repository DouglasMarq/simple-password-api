package com.douglasmarq.password.simple_password.application.controller;

import com.douglasmarq.password.simple_password.domain.dto.PasswordRequest;
import com.douglasmarq.password.simple_password.domain.dto.PasswordResponse;
import com.douglasmarq.password.simple_password.domain.service.PasswordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/password")
@AllArgsConstructor
@Tag(name = "Password", description = "Password API")
@Slf4j
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(
            summary = "Password Validation",
            description =
                    """
                    Nine or more characters with the following criteria:
                    \n• At least 1 number
                    \n• At least 1 lowercase letter
                    \n• At least 1 uppercase letter
                    \n• At least 1 special character -
                    \n• Consider the following characters as special: !@#$%^&*()-+
                    \n• Do not have any repeating characters
                    """)
    @ApiResponse(
            responseCode = "200",
            description = "Password is Valid",
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordResponse.class)))
    @ApiResponse(
            responseCode = "400",
            description = "Password is invalid",
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordResponse.class)))
    @PostMapping("/validate")
    public ResponseEntity<PasswordResponse> validatePassword(
            @Valid @RequestBody PasswordRequest request) {
        log.info("Validating password");

        return ResponseEntity.ok(
                PasswordResponse.builder()
                        .valid(passwordService.isValidPassword(request.getPassword()))
                        .build());
    }
}
