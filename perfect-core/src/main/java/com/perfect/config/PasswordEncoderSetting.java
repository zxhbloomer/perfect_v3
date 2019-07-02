package com.perfect.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderSetting {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
}
