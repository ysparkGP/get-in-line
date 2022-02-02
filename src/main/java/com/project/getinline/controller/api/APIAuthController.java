package com.project.getinline.controller.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class APIAuthController {

    @GetMapping("/signup")
    public String signUp(){
        return "done.";
    }

    @GetMapping("/login")
    public String logIn(){
        return "done.";
    }
}
