package com.salehi.socialmedia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller for dispatching to basic pages like login ,index or signup page
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String goToIndex() {
        return "index";
    }

    @RequestMapping("/login")
    public String goToLoginPage(){
        return "/authentication/login";
    }

    @RequestMapping("/signup")
    public String goToSignUpPage(){
        return "/authentication/signup";
    }

}
