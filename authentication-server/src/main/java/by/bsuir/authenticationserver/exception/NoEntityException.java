package by.bsuir.authenticationserver.exception;

public class NoEntityException extends Exception {

    public NoEntityException() {
        super();
    }

    public NoEntityException(Throwable cause) {
        super(cause);
    }

    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
