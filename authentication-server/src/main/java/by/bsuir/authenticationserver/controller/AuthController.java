package by.bsuir.authenticationserver.controller;

import by.bsuir.authenticationserver.model.dto.AuthRequest;
import by.bsuir.authenticationserver.model.dto.AuthResponse;
import by.bsuir.authenticationserver.model.dto.SignUpRequest;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.sighUp(signUpRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<Long> validateToken(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Boolean> invalidateToken(@RequestBody String jwt) {
        return ResponseEntity.ok(authService.logout(jwt));
    }
}
