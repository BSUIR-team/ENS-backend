package by.bsuir.authenticationserver.exception;

import io.jsonwebtoken.JwtException;

public class InvalidTokenException extends JwtException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
