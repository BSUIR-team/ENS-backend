package by.bsuir.templateserver.exception;

import jakarta.persistence.EntityExistsException;

public class TemplateAlreadyExistsException extends EntityExistsException {

    public TemplateAlreadyExistsException(String message) {
        super(message);
    }
}
