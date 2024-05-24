package by.bsuir.authenticationserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ioe) {
        return new ResponseEntity<>("Your authentication is expired", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException nfe) {
        return new ResponseEntity<>("Username not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException uaee) {
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }
}
