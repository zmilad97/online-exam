package com.github.zmilad97.onlineExam.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String home(){
        return "home.html";
    }


    @GetMapping("index")
    public String index() {
        return "index.html";
    }

    @GetMapping("login")
    public String login() {
        return "/login.html";
    }

    @GetMapping("logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("signUp")
    public String signUp() {
        return "/signUp.html";
    }

}
