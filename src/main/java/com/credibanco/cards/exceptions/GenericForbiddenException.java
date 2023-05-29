package com.credibanco.cards.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class GenericForbiddenException extends BankException {

    public GenericForbiddenException(ErrorCode code) {
        super(code);
    }

    public GenericForbiddenException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
