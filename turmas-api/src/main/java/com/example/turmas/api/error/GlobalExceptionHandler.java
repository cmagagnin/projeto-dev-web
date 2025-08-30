package com.example.turmas.api.error;

import com.example.turmas.shared.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return body(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST, "Erro de validação", request);

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleUnreadable(HttpMessageNotReadableException ex, WebRequest request) {
        return body(HttpStatus.BAD_REQUEST, "Requisição inválida", "Corpo da requisição não pôde ser lido.", request);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<Object> handleErrorResponse(ErrorResponseException ex, WebRequest request) {
        HttpStatusCode status = ex.getStatusCode();
        return body(HttpStatus.valueOf(status.value()), "Erro", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
        return body(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", "Ocorreu um erro inesperado.", request);
    }

    // ===== Helpers =====
    private ResponseEntity<Object> body(HttpStatus status, String error, String message, WebRequest request) {
        Map<String, Object> body = baseBody(status, error, request);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> baseBody(HttpStatus status, String error, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return body;
    }
}
