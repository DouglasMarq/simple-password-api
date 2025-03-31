package com.douglasmarq.password.simple_password.domain.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class PasswordService {

    private static final String pattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-+]).*$";

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 9) {
            log.info("Password is either null or have less than 9 characters");
            return false;
        }

        if (hasRepeatedCharacters(password)) {
            log.info("Password has repeated characters");
            return false;
        }

        return Pattern.compile(pattern).matcher(password).matches();
    }

    private boolean hasRepeatedCharacters(String password) {
        return password.chars().boxed().collect(groupingBy(c -> c, counting())).values().stream()
                .anyMatch(count -> count > 1);
    }
}
