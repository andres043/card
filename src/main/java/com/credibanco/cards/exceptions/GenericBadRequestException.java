package com.credibanco.cards.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericBadRequestException extends BankException {
    public GenericBadRequestException(ErrorCode code) {
        super(code);
    }

    public GenericBadRequestException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
