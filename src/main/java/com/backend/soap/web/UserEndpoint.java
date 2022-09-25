package com.backend.soap.web;

import com.backend.soap.service.UserService;
import com.backend.soap.utils.ValidationUtil;
import com.backend.soap.web.users.*;
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
        GetUserResponse response = new GetUserResponse();
        response.setUserTO(userService.getUserById(request.getValue().getLogin()));
        return createJaxbElement(response, GetUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public JAXBElement<GetAllUsersResponse> getAllUsers(@RequestPayload JAXBElement<GetAllUsersRequest> request) {
        log.info("getAllUsers");
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.getUserTOWithoutRolesList().addAll(userService.getAllUsers());
        return createJaxbElement(response, GetAllUsersResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public JAXBElement<DeleteUserResponse> deleteUser(@RequestPayload JAXBElement<DeleteUserRequest> request) {
        log.info("deleteUser {}", request.getValue().getLogin());
        RequestStatus requestStatus = new RequestStatus();
        if (userService.deleteUser(request.getValue().getLogin()) == 0) {
            requestStatus.setErrors("User is not found");
        } else requestStatus.setSuccess(true);
        DeleteUserResponse response = new DeleteUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, DeleteUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public JAXBElement<AddUserResponse> addUser(@RequestPayload JAXBElement<AddUserRequest> request) {
        log.info("addUser {}", request.getValue().getUserTO());
        List<String> errors = ValidationUtil.validateUserTO(request.getValue().getUserTO());
        RequestStatus requestStatus = new RequestStatus();
        if (errors.isEmpty()) {
            requestStatus.setSuccess(true);
            userService.addUser(request.getValue().getUserTO());
        } else {
            requestStatus.setErrors(StringUtils.join(errors, ", "));
        }
        AddUserResponse response = new AddUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, AddUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public JAXBElement<UpdateUserResponse> updateUser(@RequestPayload JAXBElement<UpdateUserRequest> request) {
        log.info("updateUser {}", request.getValue().getUserTO());
        RequestStatus requestStatus = new RequestStatus();
        List<String> errors = ValidationUtil.validateUserTO(request.getValue().getUserTO());
        if (errors.isEmpty()) {
            requestStatus.setSuccess(true);
            userService.updateUser(request.getValue().getUserTO());
        } else {
            requestStatus.setErrors(StringUtils.join(errors, ", "));
        }
        UpdateUserResponse response = new UpdateUserResponse();
        userService.updateUser(request.getValue().getUserTO());
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, UpdateUserResponse.class);
    }


    private <T> JAXBElement<T> createJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}