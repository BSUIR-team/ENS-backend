package by.bsuir.authenticationserver.model.dto;

public record AuthResponse(
        String username,
        String jwt,
        String message
) {
}
