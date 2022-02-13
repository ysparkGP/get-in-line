package com.project.getinline.controller.api;


import com.project.getinline.dto.APIDataResponse;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api")
//@RestController
public class APIAuthController {

    @PostMapping("/signup")
    public String signUp(){
        return "done.";
    }

    @PostMapping("/login")
    public String logIn(){
        return "done.";
    }
//
//    @PostMapping("/signup")
//    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest){
//        return APIDataResponse;
//    }
//
//    @PostMapping("/login")
//    public APIDataResponse<String> logIn(@RequestBody LoginRequest loginRequest){
//
//        return APIDataResponse;
//    }

}
