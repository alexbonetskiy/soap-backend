package com.backend.soap.service;

import com.backend.soap.domain.Role;
import com.backend.soap.domain.User;
import com.backend.soap.repository.RoleRepository;
import com.backend.soap.repository.UserRepository;
import com.backend.soap.utils.mapstruct.UserMapper;
import com.backend.soap.web.users.UserTO;
import com.backend.soap.web.users.UserTOWithoutRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserTO getUserById(String login) {
        log.info("getUserById for {}", login);
        return UserMapper.USER_MAPPER.userTO(userRepository.findByLoginWithRoles(login).orElseThrow(EntityNotFoundException::new));
    }

    public List<UserTOWithoutRoles> getAllUsers() {
        log.info("getAllUsers");
        return UserMapper.USER_MAPPER.userTOList(userRepository.findAll());
    }


    public int deleteUser(String login) {
        log.info("deleteUser");
        return userRepository.delete(login);
    }

    public void addUser(UserTO userTO) {
        User user = userRepository.findByLogin(userTO.getLogin());
        Assert.isNull(user, "User with such login already exist");
        List<Role> roles = verifyRoles(userTO);
        log.info("addUser {}", userTO);
        user = UserMapper.USER_MAPPER.user(userTO);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUser(UserTO userTO) {
        User user = userRepository.findByLogin(userTO.getLogin());
        Assert.notNull(user, "User with such login doesn't exist");
        List<Role> roles = verifyRoles(userTO);
        log.info("updateUser {}", userTO);
        user = UserMapper.USER_MAPPER.user(userTO);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public List<Role> verifyRoles(UserTO userTO) {
        List<Role> roles = new ArrayList<>();
        for (String roleName : userTO.getRole()) {
            Role role = roleRepository.findByName(roleName);
            Assert.notNull(role, roleName + " role doesn't exist");
            roles.add(role);
        }
        return roles;
    }
}
