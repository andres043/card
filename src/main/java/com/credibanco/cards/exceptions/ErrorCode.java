package com.credibanco.cards.exceptions;

public interface ErrorCode {
    String EXCEPTION = "exception.";

    String name();

    default String messageCode() {
        return EXCEPTION + this.name();
    }
}
