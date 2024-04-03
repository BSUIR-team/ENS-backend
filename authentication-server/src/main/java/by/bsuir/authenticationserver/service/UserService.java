package by.bsuir.authenticationserver.service;

import by.bsuir.authenticationserver.model.entity.User;

public interface UserService {

    User getUserByEmail(String email);

}
