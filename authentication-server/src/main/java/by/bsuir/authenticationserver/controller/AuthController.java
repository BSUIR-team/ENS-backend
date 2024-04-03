package by.bsuir.authenticationserver.controller;

import by.bsuir.authenticationserver.model.dto.AuthenticationRequest;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthenticationRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        String password = user.getPassword();

        return null;
    }
}
