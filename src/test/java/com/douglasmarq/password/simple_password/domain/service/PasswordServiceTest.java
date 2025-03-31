package com.douglasmarq.password.simple_password.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceTest {

    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        if (passwordService == null) {
            passwordService = new PasswordService();
        }
    }

    @Test
    @DisplayName("Should return false when password is null")
    void isValidPasswordWhenPasswordIsNullThenReturnFalse() {
        boolean result = passwordService.isValidPassword(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when password is less than 9 characters")
    void isValidPasswordWhenPasswordLessThan9CharsThenReturnFalse() {
        String shortPassword = "Aa1$bcde";

        boolean result = passwordService.isValidPassword(shortPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when password doesn't contain digits")
    void isValidPasswordWhenPasswordWithoutDigitsThenReturnFalse() {
        String noDigitsPassword = "PasswordTest$";

        boolean result = passwordService.isValidPassword(noDigitsPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when password doesn't contain lowercase letters")
    void isValidPasswordWhenPasswordWithoutLowercaseThenReturnFalse() {
        String noLowercasePassword = "PASSWORD123$";

        boolean result = passwordService.isValidPassword(noLowercasePassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when password doesn't contain uppercase letters")
    void isValidPassword_whenPasswordWithoutUppercaseThenReturnFalse() {
        String noUppercasePassword = "password123$";

        boolean result = passwordService.isValidPassword(noUppercasePassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when password doesn't contain special characters")
    void isValidPasswordWhenPasswordWithoutSpecialCharsThenReturnFalse() {
        String noSpecialCharsPassword = "Password123";

        boolean result = passwordService.isValidPassword(noSpecialCharsPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when password meets all criteria")
    void isValidPasswordWhenValidPasswordThenReturnTrue() {
        String validPassword = "Pasword12$";

        boolean result = passwordService.isValidPassword(validPassword);

        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Should validate various valid passwords")
    @ValueSource(
            strings = {
                "Pasword12$",
                "SecuraP@z1234",
                "Test!1234Abc",
                "C0mpl3x#P@s5",
                "V3ryS4cule!Pas5"
            })
    void isValidPasswordWithValidPasswordsShouldReturnTrue(String validPassword) {
        boolean result = passwordService.isValidPassword(validPassword);

        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Should reject invalid passwords")
    @ValueSource(strings = {"onlylow3r$", "ONLYUPPER3$", "Password$", "Password123", "Short1$"})
    void isValidPasswordWithInvalidPasswordsShouldReturnFalse(String invalidPassword) {
        boolean result = passwordService.isValidPassword(invalidPassword);

        assertFalse(result);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Should reject null password")
    void isValidPasswordWithNullPasswordShouldReturnFalse(String nullPassword) {
        boolean result = passwordService.isValidPassword(nullPassword);

        assertFalse(result);
    }
}
