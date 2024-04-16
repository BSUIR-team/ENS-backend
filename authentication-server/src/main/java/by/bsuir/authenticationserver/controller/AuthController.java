package by.bsuir.authenticationserver.controller;

import by.bsuir.authenticationserver.model.dto.AuthRequest;
import by.bsuir.authenticationserver.model.dto.TokenResponse;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.sighUp(authRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<Long> validateToken(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getId());
    }
}
