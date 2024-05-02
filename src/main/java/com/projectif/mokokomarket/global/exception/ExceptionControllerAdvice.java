package com.projectif.mokokomarket.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Order(1)
public class ExceptionControllerAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {

        log.error("RuntimeException handle = {}", e.getMessage(), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {

        log.error("UsernameNotFoundException handle = {}", e.getMessage(), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Bean Validation Exception 예외 직접 핸들링
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

//        log.error("MethodArgumentNotValidException handle = {}", e.getMessage(), e);
        log.error("MethodArgumentNotValidException handle");

        List<ObjectError> allErrors = e.getAllErrors();
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError objectError : allErrors) {
            log.error("ObjectError = {}", objectError.getDefaultMessage());
            errorMessages.add(objectError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
