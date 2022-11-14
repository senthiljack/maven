package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.entity.Role;
import com.recodesolutions.itticket.entity.User;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.repository.RoleRepository;
import com.recodesolutions.itticket.repository.UserRepository;
import com.recodesolutions.itticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, List<String> roles){
        Set<Role> roleSet = roleRepository.findByNameIn(roles);
        if(roleSet.isEmpty()){
            throw new ReITException(ErrorCode.ROLE_NOT_FOUND, ErrorMessages.ROLE_NOT_FOUND);
        }
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    @Override
    public void updateUserRoles(List<String> roleNeeded,User user) {
        Set<Role> roleSet = roleRepository.findByNameIn(roleNeeded);
        user.setRoles(roleSet);
        userRepository.save(user);
    }
}
