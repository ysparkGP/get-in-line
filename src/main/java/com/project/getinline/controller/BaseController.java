package com.project.getinline.controller;


import com.project.getinline.exception.GeneralException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController  {

    @GetMapping("/")
    public String root(){
        return "index";
    }

}
