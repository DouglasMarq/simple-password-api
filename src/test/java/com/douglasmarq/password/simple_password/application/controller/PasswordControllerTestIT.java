package com.douglasmarq.password.simple_password.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douglasmarq.password.simple_password.domain.dto.PasswordRequest;
import com.douglasmarq.password.simple_password.domain.service.PasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PasswordControllerTestIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        if (passwordService == null) {
            passwordService = new PasswordService();
        }
    }

    private PasswordRequest createPassword(String password) {
        return PasswordRequest.builder().password(password).build();
    }

    @Test
    @DisplayName("Should validate a valid password and return true")
    public void shouldValidateValidPassword() throws Exception {
        PasswordRequest request = createPassword("Test1234!");

        mockMvc.perform(
                        post("/v1/password/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "short1!",
                "passwordonly",
                "PASSWORD123!",
                "Password!!!",
                "Password123",
                "Passw0rd!d!",
                "null"
            })
    @DisplayName("Should return invalid for passwords that don't meet criteria")
    public void shouldReturnInvalidForInvalidPasswords(String password) throws Exception {
        PasswordRequest request = createPassword(password);

        mockMvc.perform(
                        post("/v1/password/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false));
    }

    @Test
    @DisplayName("Should return invalid for null password")
    public void shouldReturnInvalidForNullPassword() throws Exception {
        PasswordRequest request = createPassword(null);

        mockMvc.perform(
                        post("/v1/password/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 Bad Request for missing request body")
    public void shouldReturn400ForMissingRequestBody() throws Exception {
        mockMvc.perform(post("/v1/password/validate").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should validate complex valid passwords")
    public void shouldValidateComplexValidPasswords() throws Exception {
        String[] validPasswords = {"ABCdef123!", "Pasword12@", "C0mplex!ty"};

        for (String validPassword : validPasswords) {
            PasswordRequest request = createPassword(validPassword);

            mockMvc.perform(
                            post("/v1/password/validate")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.valid").value(true));
        }
    }
}
