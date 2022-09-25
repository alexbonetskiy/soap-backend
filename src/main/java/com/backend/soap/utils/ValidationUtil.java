package com.backend.soap.utils;

import com.backend.soap.web.users.UserTO;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
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
        }
        if (!userTO.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9]).*$")) {
            result.add("Password must contain one capital letter and one digit at least");
        }
        return result;
    }

}
