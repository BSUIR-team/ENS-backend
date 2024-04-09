package by.bsuir.authenticationserver.controller;

import by.bsuir.authenticationserver.model.dto.AuthRequest;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
//@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody AuthRequest request) {
        Optional<User> optional = authService.getUser(request.getEmail());
        return optional.map(user -> ResponseEntity.ok(authService.generateToken(user.getUsername())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        return authService.saveUser(user);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        authService.validateToken(token); // throws exception
        return "token is valid";
    }
}
