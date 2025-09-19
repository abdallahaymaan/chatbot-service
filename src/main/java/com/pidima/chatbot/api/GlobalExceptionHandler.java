package com.pidima.chatbot.api;

import com.pidima.chatbot.api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .findFirst()
            .orElse("Validation error");
        ErrorResponse body = new ErrorResponse(request.getDescription(false), message, "VALIDATION_ERROR");
        return ResponseEntity.badRequest().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse body = new ErrorResponse(request.getDescription(false), ex.getMessage(), "MISSING_PARAMETER");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(request.getRequestURI(), ex.getMessage(), "BAD_REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(request.getRequestURI(), "Internal server error", "INTERNAL_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}


