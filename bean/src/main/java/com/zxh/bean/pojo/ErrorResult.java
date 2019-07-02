package com.zxh.bean.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResult<T> {

    @JSONField
    private String timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

    private T exception;
}
