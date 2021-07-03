package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UsersController {
    private UsersService usersService;


    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "signUp.do")
    public String userSignup(@ModelAttribute Users users) {
        try {
            usersService.save(users);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in user signup");
            return "/authentication/signup";
        }
    }

}