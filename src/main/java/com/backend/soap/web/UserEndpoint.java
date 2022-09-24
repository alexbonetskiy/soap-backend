package com.backend.soap.web;

import com.backend.soap.service.UserService;
import com.backend.soap.web.users.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;


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
            requestStatus.setSuccess(false);
            requestStatus.setErrors("User not found");
        } else requestStatus.setSuccess(true);
        DeleteUserResponse response = new DeleteUserResponse();
        response.setRequestStatus(requestStatus);
        return createJaxbElement(response, DeleteUserResponse.class);
    }


    private <T> JAXBElement<T> createJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}