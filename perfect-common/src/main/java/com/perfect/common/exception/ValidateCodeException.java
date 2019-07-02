package com.perfect.common.exception;

import com.perfect.common.Enum.ResultEnum;
import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7525757620869234981L;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ValidateCodeException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public ValidateCodeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
