package by.bsuir.authenticationserver.service;

import by.bsuir.authenticationserver.exception.InvalidTokenException;
import by.bsuir.authenticationserver.exception.UserJwtNotFoundException;
import by.bsuir.authenticationserver.model.entity.Token;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthService authService;

    public void generateToken(User user, String jwt) {
        tokenRepository.save(Token.builder()
                .user(user)
                .jwt(jwt)
                .isExpired(false)
                .build()
        );
    }

    public void deletePreviousToken(User user) {
        tokenRepository.findByUserId(user.getId()).ifPresent(tokenRepository::delete);
    }

    public Boolean validateToken(String jwt) {
        boolean isValid = tokenRepository.findByJwt(jwt)
                .map(token -> !token.isExpired())
                .orElse(false);
        if (isValid && jwtService.validateToken(jwt)) {
            return true;
        } else {
            throw new InvalidTokenException("Invalid token");
        }
    }

    public User takeUserFromJwt(String jwt) {
        String email = jwtService.extractEmail(jwt);
        try {
            return authService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            throw new UserJwtNotFoundException(e.getMessage());
        }
    }
}
