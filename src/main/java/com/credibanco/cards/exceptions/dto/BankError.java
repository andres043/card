package com.credibanco.cards.exceptions.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class BankError {
    private final Error error;

    @Getter
    @Builder
    public static class Error {
        private final String message;
        private final HttpStatus status;

        public int getCode() {
            return status.value();
        }
    }
}
