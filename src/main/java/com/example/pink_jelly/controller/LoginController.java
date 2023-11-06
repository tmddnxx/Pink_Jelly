package com.example.pink_jelly.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("")
    public String login(){

        return "/login/login";
    }
}
