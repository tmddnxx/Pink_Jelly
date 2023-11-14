package com.example.pink_jelly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String login(String error, String logout){
        log.info("login get...");
        log.info("logout: " + logout);

        return "/login/login";
    }

}
