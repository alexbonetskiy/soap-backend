package com.backend.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}
