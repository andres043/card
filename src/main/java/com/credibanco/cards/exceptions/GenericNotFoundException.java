package com.credibanco.cards.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GenericNotFoundException extends BankException {
    public GenericNotFoundException(ErrorCode code) {
        super(code);
    }

    public GenericNotFoundException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
