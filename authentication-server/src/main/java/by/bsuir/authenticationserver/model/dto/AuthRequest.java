package by.bsuir.authenticationserver.model.dto;

public record AuthRequest(
        String email,
        String password
) {
}
