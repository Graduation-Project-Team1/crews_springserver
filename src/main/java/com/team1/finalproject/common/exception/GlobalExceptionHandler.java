package com.team1.finalproject.common.exception;

import com.team1.finalproject.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<?> handleGlobalException(GlobalException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(
                        e.getErrorCode().getCode(),
                        e.getErrorCode().getMessage())
                );
    }

    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleClassNotFoundException(ClassNotFoundException e) {
        return ResponseEntity.status(400)
                .body(ResponseDto.fail(
                        "404 Not Found",
                        e.getMessage())
                );
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(400)
                .body(ResponseDto.fail(
                        "404 Not Found",
                        "NoSuchElementException: No value present.")
                );
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleIOException(IOException e) {
        return ResponseEntity.status(400)
                .body(ResponseDto.fail(
                        "500 Server Error",
                        "IO Error: Error on File")
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(400)
                .body(ResponseDto.fail(
                        "404 Not Found",
                        e.getMessage())
                );
    }
    //UsernameNotFoundException
}

