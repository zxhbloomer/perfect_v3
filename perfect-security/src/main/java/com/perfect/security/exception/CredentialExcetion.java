package com.perfect.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CredentialExcetion extends AuthenticationException {

    private static final long serialVersionUID = -920087729589688230L;

    public CredentialExcetion(String message) {
        super(message);
    }
}
