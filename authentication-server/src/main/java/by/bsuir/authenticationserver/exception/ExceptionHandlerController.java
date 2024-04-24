package by.bsuir.authenticationserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ioe) {
        return new ResponseEntity<>("Your authentication is expired", HttpStatus.UNAUTHORIZED);
    }
}
