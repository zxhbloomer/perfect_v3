package com.perfect.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @description: 用户登录数据传输对象
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = -2008867352253446153L;
//    @NotBlank(message = "用户名不能为空")
    private String username;
//    @NotBlank(message = "密码不能为空")
    private String password;
}