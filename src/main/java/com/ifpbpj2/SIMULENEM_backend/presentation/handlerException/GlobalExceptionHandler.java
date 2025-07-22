package com.ifpbpj2.SIMULENEM_backend.presentation.handlerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifpbpj2.SIMULENEM_backend.exception.EntityInUseException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorDetails(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                                ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                        HttpServletRequest request, BindingResult result) {
                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorDetails(request, HttpStatus.UNPROCESSABLE_ENTITY,
                                                "Invalid inputs", result));
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorDetails(request, HttpStatus.BAD_REQUEST,
                                                ex.getMessage()));
        }

        @ExceptionHandler(EntityInUseException.class)
        public ResponseEntity<ErrorDetails> handleEntityInUseException(EntityInUseException ex,
                        HttpServletRequest request) {
                return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorDetails(request, HttpStatus.CONFLICT,
                                                ex.getMessage()));
        }

}
