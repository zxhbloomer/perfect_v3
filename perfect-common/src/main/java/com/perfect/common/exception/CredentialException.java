package com.perfect.common.exception;

import org.springframework.security.core.AuthenticationException;

public class CredentialException extends AuthenticationException {

    private static final long serialVersionUID = -920087729589688230L;

    public CredentialException(String message) {
        super(message);
    }
}
