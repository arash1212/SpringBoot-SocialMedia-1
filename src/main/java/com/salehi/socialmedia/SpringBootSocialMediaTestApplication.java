package com.salehi.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class SpringBootSocialMediaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSocialMediaTestApplication.class, args);
    }

    @RequestMapping("/")
    public String goToIndex(){
        return "index";
    }

}
