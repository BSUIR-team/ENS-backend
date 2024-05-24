package by.bsuir.authenticationserver.exception;

import io.jsonwebtoken.JwtException;

public class UserJwtNotFoundException extends JwtException {

    public UserJwtNotFoundException(String message) {
        super(message);
    }

}
