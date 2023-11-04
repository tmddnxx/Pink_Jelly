package com.example.pink_jelly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/catsMe")
@RequiredArgsConstructor
public class CatsMeController {

    @GetMapping("/board/write")
    public void write(){

    }
    @GetMapping("/board/modify")
    public void modify(){

    }

}
