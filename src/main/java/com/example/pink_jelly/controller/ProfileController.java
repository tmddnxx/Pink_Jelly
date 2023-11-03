package com.example.pink_jelly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("")
    public String profile(){

        return "profile/profile";
    }

    @GetMapping("/friendProfile")
    public void friendProfile(){

    }

}
