package by.bsuir.authenticationserver.service;

import by.bsuir.authenticationserver.exception.UserAlreadyExistsException;
import by.bsuir.authenticationserver.exception.UserNotFoundException;
import by.bsuir.authenticationserver.model.dto.AuthRequest;
import by.bsuir.authenticationserver.model.dto.AuthResponse;
import by.bsuir.authenticationserver.model.dto.SignUpRequest;
import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public Boolean sighUp(SignUpRequest request) {
        return Optional.of(userRepository.findByEmail(request.email()))
                .map(user -> {
                    if (user.isPresent()) {
                        throw new UserAlreadyExistsException(String.format("User with email %s already exists", request.email()));
                    } else {
                        return request;
                    }
                })
                .map(req -> User.builder()
                        .email(request.email())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .build())
                .map(userRepository::saveAndFlush)
                .isPresent();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
        } catch (Exception e) {
            throw new UserNotFoundException(String.format("User with email %s not found", authRequest.email()));
        }
        User user = loadUserByUsername(authRequest.email());
        tokenService.deletePreviousToken(user);
        String jwt = jwtService.generateToken(user);
        tokenService.generateToken(user, jwt);
        return new AuthResponse(
                user.getUsername(),
                jwt,
                "Sample message"
                );
    }

    public Boolean logout(String jwt) {
        return tokenService.invalidateToken(jwt);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
