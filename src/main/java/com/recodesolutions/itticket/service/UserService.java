package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);

    User createUser(User user, List<String> roles);

    void updateUserRoles(List<String> roleNeeded,User user);
}
