package by.bsuir.apiserver.exception;

import jakarta.persistence.EntityNotFoundException;

public class TemplateRecipientsNotFoundException extends EntityNotFoundException {

    public TemplateRecipientsNotFoundException(String message) {
        super(message);
    }
}
