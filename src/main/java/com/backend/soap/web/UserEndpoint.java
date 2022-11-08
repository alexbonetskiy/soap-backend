package com.backend.soap.web;

import com.backend.soap.domain.User;
import com.backend.soap.dto.*;
import com.backend.soap.service.UserService;
import com.backend.soap.utils.ValidationUtil;
import com.backend.soap.utils.mapstruct.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;


@RequiredArgsConstructor
@Endpoint
@Slf4j
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://backend.com/soap/web/users";

    private final UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public JAXBElement<GetUserResponse> getUser(@RequestPayload JAXBElement<GetUserRequest> request) {
        log.info("getUser for {}", request.getValue().getLogin());
        var response = new GetUserResponse();
        UserTO userTO = UserMapper.USER_MAPPER.userTO(userService.getUserById(request.getValue().getLogin()));
        response.setUserTO(userTO);
        return createJaxbElement(response, GetUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public JAXBElement<GetAllUsersResponse> getAllUsers(@RequestPayload JAXBElement<GetAllUsersRequest> request) {
        log.info("getAllUsers");
        var response = new GetAllUsersResponse();
        List<User> userList = userService.getAllUsers();
        response.getUserTOWithoutRolesList().addAll(UserMapper.USER_MAPPER.userTOList(userList));
        return createJaxbElement(response, GetAllUsersResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public JAXBElement<DeleteUserResponse> deleteUser(@RequestPayload JAXBElement<DeleteUserRequest> request) {
        log.info("deleteUser {}", request.getValue().getLogin());
        var requestStatus = new RequestStatus();
        if (userService.deleteUser(request.getValue().getLogin())) {
            requestStatus.setSuccess(true);
        } else requestStatus.getErrors().add("Such user doesn't exist");
        var response = new DeleteUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, DeleteUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public JAXBElement<AddUserResponse> addUser(@RequestPayload JAXBElement<AddUserRequest> request) {
        log.info("validate userTO={}", request.getValue().getUserTO());
        List<String> validationResult = ValidationUtil.validateUserTO(request.getValue().getUserTO());
        var requestStatus = new RequestStatus();
        if (validationResult.isEmpty()) {
            log.info("add user");
            userService.addUser(UserMapper.USER_MAPPER.user(request.getValue().getUserTO()));
            requestStatus.setSuccess(true);
        } else {
            requestStatus.getErrors().addAll(validationResult);
        }
        var response = new AddUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, AddUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public JAXBElement<UpdateUserResponse> updateUser(@RequestPayload JAXBElement<UpdateUserRequest> request) {
        log.info("validate userTO={}", request.getValue().getUserTO());
        List<String> validationResult = ValidationUtil.validateUserTO(request.getValue().getUserTO());
        var requestStatus = new RequestStatus();
        if (validationResult.isEmpty()) {
            log.info("update user");
            userService.updateUser(UserMapper.USER_MAPPER.user(request.getValue().getUserTO()));
            requestStatus.setSuccess(true);
        } else {
            requestStatus.getErrors().addAll(validationResult);
        }
        var response = new UpdateUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, UpdateUserResponse.class);
    }


    private <T> JAXBElement<T> createJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}