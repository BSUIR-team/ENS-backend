package by.bsuir.authenticationserver.exception;

import jakarta.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
