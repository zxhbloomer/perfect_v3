package com.perfect.common.exception;
import org.springframework.security.core.AuthenticationException;

/**
 * 插入出错异常
 */
public class InsertErrorException extends AuthenticationException {

    private static final long serialVersionUID = 5022575393500654458L;

    public InsertErrorException(String message) {
        super(message);
    }
}