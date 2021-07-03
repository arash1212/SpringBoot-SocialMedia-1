package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Authorities;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.AuthoritiesService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UsersController {
    private UsersService usersService;
    private PasswordEncoder passwordEncoder;
    private AuthoritiesService authoritiesService;

    @Autowired
    public UsersController(UsersService usersService, PasswordEncoder passwordEncoder, AuthoritiesService authoritiesService) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesService = authoritiesService;
    }

    @RequestMapping(value = "signUp.do")
    public String userSignup(@ModelAttribute Users users) {
        try {
            //encoding user inserted password before saving the new user
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            //
            //saving user authority at signup (as user)
            Authorities authorities = new Authorities().setUsername(users.getUsername()).setAuthority("user");
            authoritiesService.save(authorities);
            //
            usersService.save(users);
            System.out.println("user saved");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in user signup");
            return "/authentication/signup";
        }
    }

}
