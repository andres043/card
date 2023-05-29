package com.credibanco.cards.exceptions.handler;

import com.credibanco.cards.exceptions.BankException;
import com.credibanco.cards.exceptions.dto.BankError;
import com.credibanco.cards.utils.messages.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BankExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageHandler messageHandler;


    @ExceptionHandler(BankException.class)
    public ResponseEntity<BankError> handleOneException(final BankException ex, final WebRequest request) {
        logger.error("There is a BankException with code", ex);
        final BankError error = BankError.builder()
                .error(buildError(ex))
                .build();

        return new ResponseEntity<>(error, new HttpHeaders(), error.getError().getStatus());
    }

    @Override
    @SneakyThrows
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                body.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        final BankError bankError = BankError.builder()
                .error(buildError(status, body.toString()))
                .build();

        return new ResponseEntity<>(bankError, headers, status);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final BankError bankError = BankError.builder()
                .error(buildError(status, ex.getCause().getMessage()))
                .build();

        return new ResponseEntity<>(bankError, headers, status);
    }

    private BankError.Error buildError(BankException ex) {
        ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);

        return BankError.Error.builder()
                .message(messageHandler.getMessage(ex.getCode().messageCode()))
                .status(status.value())
                .build();
    }

    private BankError.Error buildError(HttpStatus status, String message) {
        return BankError.Error.builder()
                .message(message)
                .status(status)
                .build();
    }
}
