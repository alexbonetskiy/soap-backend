package com.backend.soap.utils;

import com.backend.soap.repository.RoleRepository;
import com.backend.soap.repository.UserRepository;
import com.backend.soap.web.users.UserTO;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ValidationUtil {

    public static List<String> validateUserTOForAdding(UserTO userTO, UserRepository userRepository, RoleRepository roleRepository) {
        List<String> result = new ArrayList<>();
        if (userRepository.findByLogin(userTO.getLogin()) != null) {
            result.add("User with such login already exist");
        }
        result.addAll(validateRoles(userTO, roleRepository));
        result.addAll(validateUserTO(userTO));
        return result;
    }

    public static List<String> validateUserTOForUpdating(UserTO userTO, UserRepository userRepository, RoleRepository roleRepository) {
        List<String> result = new ArrayList<>();
        if (userRepository.findByLogin(userTO.getLogin()) == null) {
            result.add("User with such login doesn't exist");
        }
        result.addAll(validateRoles(userTO, roleRepository));
        result.addAll(validateUserTO(userTO));
        return result;
    }


    public static List<String> validateRoles(UserTO userTO, RoleRepository roleRepository) {
        List<String> result = new ArrayList<>();
        for (String roleName : userTO.getRole()) {
            if (roleRepository.findByName(roleName) == null) {
                result.add(roleName + " role doesn't exist");
            }
        }
        return result;
    }


    public static List<String> validateUserTO(UserTO userTO) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isBlank(userTO.getName())) {
            result.add("Enter user name");
        }
        if (StringUtils.isBlank(userTO.getLogin())) {
            result.add("Enter user login");
        }
        if (StringUtils.isBlank(userTO.getPassword())) {
            result.add("Enter user password");
        } else if (!userTO.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9]).*$")) {
            result.add("Password must contain one capital letter and one digit at least");
        }
        return result;
    }

}
