package com.credibanco.cards.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class BankException extends RuntimeException {
    private final ErrorCode code;

    protected BankException(ErrorCode code) {
        super(code.name());
        this.code = code;
    }

    protected BankException(ErrorCode code, Throwable cause) {
        super(code.name(), cause);
        this.code = code;
    }
}
