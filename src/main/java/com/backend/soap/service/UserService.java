package com.backend.soap.service;

import com.backend.soap.domain.Role;
import com.backend.soap.domain.User;
import com.backend.soap.repository.RoleRepository;
import com.backend.soap.repository.UserRepository;
import com.backend.soap.utils.mapstruct.UserMapper;
import com.backend.soap.web.users.RequestStatus;
import com.backend.soap.web.users.UserTO;
import com.backend.soap.web.users.UserTOWithoutRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.backend.soap.utils.ValidationUtil.validateUserTOForAdding;
import static com.backend.soap.utils.ValidationUtil.validateUserTOForUpdating;

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


    public RequestStatus deleteUser(String login) {
        RequestStatus requestStatus = new RequestStatus();
        if (userRepository.delete(login) == 0) {
            requestStatus.setErrors("Such user doesn't exist");
            return requestStatus;
        }
        log.info("deleteUser {}", login);
        requestStatus.setSuccess(true);
        return requestStatus;
    }

    public RequestStatus addUser(UserTO userTO) {
        RequestStatus requestStatus = createRequestStatus(validateUserTOForAdding(userTO, userRepository, roleRepository));
        if (!requestStatus.isSuccess()) {
            return requestStatus;
        }
        log.info("addUser {}", userTO);
        saveUser(userTO);
        return requestStatus;
    }

    public RequestStatus updateUser(UserTO userTO) {
        RequestStatus requestStatus = createRequestStatus(validateUserTOForUpdating(userTO, userRepository, roleRepository));
        if (!requestStatus.isSuccess()) {
            return requestStatus;
        }
        log.info("updateUser {}", userTO);
        saveUser(userTO);
        return requestStatus;
    }

    public void saveUser(UserTO userTO) {
        User user = UserMapper.USER_MAPPER.user(userTO);
        user.setRoles(getRolesFromTO(userTO));
        userRepository.save(user);
    }

    public List<Role> getRolesFromTO(UserTO userTO) {
        List<Role> roles = new ArrayList<>();
        for (String roleName : userTO.getRole()) {
            Role role = roleRepository.findByName(roleName);
            roles.add(role);
        }
        return roles;
    }

    public RequestStatus createRequestStatus(List<String> errors) {
        RequestStatus requestStatus = new RequestStatus();
        if (!errors.isEmpty()) {
            requestStatus.setErrors(StringUtils.join(errors, ", "));
            return requestStatus;
        }
        requestStatus.setSuccess(true);
        return requestStatus;
    }
}
