package com.perfect.bean.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResult<T> {

    private String timestamp;

    private Integer status;

    private String message;

    private String path;

    private T data;
}
