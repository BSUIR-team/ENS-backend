package by.bsuir.templateserver.exception;

import jakarta.persistence.EntityNotFoundException;

public class TemplateNotFoundException extends EntityNotFoundException {

    public TemplateNotFoundException(String message) {
        super(message);
    }
}
