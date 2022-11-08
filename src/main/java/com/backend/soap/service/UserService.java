package com.backend.soap.service;

import com.backend.soap.domain.Role;
import com.backend.soap.domain.User;
import com.backend.soap.exception.ApplicationException;
import com.backend.soap.repository.RoleRepository;
import com.backend.soap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public User getUserById(String login) {
        log.info("getUserById for {}", login);
        return userRepository.findByLoginWithRoles(login).orElseThrow(() -> new ApplicationException("Such user doesn't exist"));
    }

    public List<User> getAllUsers() {
        log.info("getAllUsers");
        return userRepository.findAll();
    }

    public boolean deleteUser(String login) {
        log.info("deleteUser {}", login);
        return userRepository.delete(login) == 0;
    }

    public void addUser(User user) {
        log.info("input user={}", user);
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ApplicationException("User with such login already exist");
        }
        List<Role> roleList = extractRoles(user);
        user.setRoles(roleList);
        log.info("add user={}", user);
        userRepository.save(user);
    }

    public void updateUser(User user) {
        log.info("input user={}", user);
        if (userRepository.findByLogin(user.getLogin()) == null) {
            throw new ApplicationException("User with such login doesn't exist");
        }
        extractRoles(user);
        log.info("update user={}", user);
        userRepository.save(user);
    }

    private List<Role> extractRoles(User user) {
        log.info("extract roles for user={}", user);
        List<Role> roleList = new ArrayList<>();
        for (Role roleName : user.getRoles()) {
            Role role = roleRepository.findByName(roleName.getName());
            if (role == null) {
                throw new ApplicationException("Such role doesn't exist");
            } else roleList.add(role);
        }
        user.setRoles(roleList);
        return roleList;
    }
}
