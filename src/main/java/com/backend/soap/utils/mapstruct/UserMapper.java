package com.backend.soap.utils.mapstruct;

import com.backend.soap.domain.Role;
import com.backend.soap.domain.User;
import com.backend.soap.web.users.UserTO;
import com.backend.soap.web.users.UserTOWithoutRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    List<UserTOWithoutRoles> userTOList(List<User> users);

    @Mapping(source = "roles",target = "role")
    UserTO userTO(User user);

    User user(UserTO userTO);

    List<String> rolesToString(List<Role> roles);

    default String map(Role role) {
        return role.getName();
    }

}
