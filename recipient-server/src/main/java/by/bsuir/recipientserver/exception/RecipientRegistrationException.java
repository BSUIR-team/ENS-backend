package by.bsuir.recipientserver.exception;

import jakarta.persistence.EntityNotFoundException;

public class RecipientRegistrationException extends EntityNotFoundException {

    public RecipientRegistrationException(String message) {
        super(message);
    }
}
