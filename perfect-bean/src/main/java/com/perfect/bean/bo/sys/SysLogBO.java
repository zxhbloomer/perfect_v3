package com.perfect.bean.bo.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统日志bo
 */
@Data
@Builder
@AllArgsConstructor
public class SysLogBO implements Serializable {

    private static final long serialVersionUID = 3217907220556047829L;

    private String className;

    private String httpMethod;

    private String classMethod;

    private String params;

    private String execTime;

    private String remark;

    private String createDate;

    private String url;

    private String ip;

}
