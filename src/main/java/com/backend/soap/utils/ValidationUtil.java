package com.backend.soap.utils;

import com.backend.soap.dto.UserTO;
import com.backend.soap.repository.RoleRepository;
import com.backend.soap.repository.UserRepository;

import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class ValidationUtil {

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
