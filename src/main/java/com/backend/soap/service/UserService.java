package com.backend.soap.service;

import com.backend.soap.repository.UserRepository;
import com.backend.soap.utils.mapstruct.UserMapper;
import com.backend.soap.web.users.UserTO;
import com.backend.soap.web.users.UserTOWithoutRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserTO getUserById(String login) {
        log.info("getUserById for {}", login);
        return UserMapper.USER_MAPPER.userTO(userRepository.findByIdWithRoles(login).orElseThrow(EntityNotFoundException::new));
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
        log.info("addUser {}", userTO);
        userRepository.save(UserMapper.USER_MAPPER.user(userTO));
    }

    public void updateUser(UserTO userTO) {
        log.info("updateUser {}", userTO);
        userRepository.save(UserMapper.USER_MAPPER.user(userTO));
    }
}
