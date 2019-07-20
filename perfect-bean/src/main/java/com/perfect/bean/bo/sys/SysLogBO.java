package com.perfect.bean.bo.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志bo
 * @author zhangxh
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

    private Long execTime;

    private String remark;

    private LocalDateTime createDate;

    private String url;

    private String ip;

}
