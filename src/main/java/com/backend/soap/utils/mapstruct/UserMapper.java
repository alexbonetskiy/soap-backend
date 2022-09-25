package com.backend.soap.utils.mapstruct;

import com.backend.soap.domain.Role;
import com.backend.soap.domain.User;
import com.backend.soap.web.users.RoleTO;
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

    @Mapping(source = "roles", target = "role")
    UserTO userTO(User user);

    @Mapping(source = "role", target = "roles")
    User user(UserTO userTO);

    List<RoleTO> roleTOList(List<Role> roles);

    RoleTO roleTO(Role order);


}
