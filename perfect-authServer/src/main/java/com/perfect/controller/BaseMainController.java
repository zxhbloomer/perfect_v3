package com.perfect.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BaseMainController {
    @Value("${loginPage}")
    private String loginPage;
    @Value("${loginProcessUrl}")
    private String loginProcessUrl;

    @GetMapping("/oauth/login")
    public String loginPage(Model model){

        model.addAttribute("loginProcessUrl",loginProcessUrl);

        return "base-login";
    }

}
