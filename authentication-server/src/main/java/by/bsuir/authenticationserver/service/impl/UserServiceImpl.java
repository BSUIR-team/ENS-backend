package by.bsuir.authenticationserver.service.impl;

import by.bsuir.authenticationserver.model.entity.User;
import by.bsuir.authenticationserver.repository.UserRepository;
import by.bsuir.authenticationserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new RuntimeException("User with such email does not exist"));
    }
}
