package com.zxh.bean.pojo;

public class ResponseExceptionBean extends ErrorResult {

    ResponseExceptionBean(String timestamp, Integer status, String error, String message, String path, Object exception) {
        super(timestamp, status, error, message, path, exception);
    }
}
