package com.desafio.luizalabs.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomExceptionHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handlerExceptions(MethodArgumentNotValidException ex)
    {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(Map.of(MESSAGE, errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseErrors() {
        String errorMessage = "Valor inválido. O campo deve ser um número do tipo Long.";

        return ResponseEntity.badRequest().body(Map.of(MESSAGE, errorMessage));
    }
}
