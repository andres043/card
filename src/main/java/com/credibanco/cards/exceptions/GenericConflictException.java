package com.credibanco.cards.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.CONFLICT)
public class GenericConflictException extends BankException {
    public GenericConflictException(ErrorCode code) {
        super(code);
    }

    public GenericConflictException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
