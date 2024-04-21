package by.bsuir.authenticationserver.model.dto;

public record SignUpRequest(
        String email,
        String username,
        String password
) {
}
