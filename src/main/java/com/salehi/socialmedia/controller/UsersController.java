package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.UserInfo;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.UserInfoService;
import com.salehi.socialmedia.model.service.UsersService;
import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UsersController {
    private UsersService usersService;
    private UserInfoService userInfoService;
    //
    private Authentication authentication;


    @Autowired
    public UsersController(UsersService usersService, UserInfoService userInfoService) {
        this.usersService = usersService;
        this.userInfoService = userInfoService;
    }

    @RequestMapping(value = "signUp.do")
    public String userSignup(@ModelAttribute Users users, @ModelAttribute UserInfo userInfo) {
        try {
            if (usersService.findUserByUsername(users.getUsername()) == null && userInfo.getName() != null && !userInfo.getName().equals("") && userInfo.getFamily() != null && !userInfo.getFamily().equals("")) {
                //
                userInfo = userInfoService.save(userInfo);
                //
                users.setUserInfo(userInfo);
                //
                usersService.save(users);
                //
                return "redirect:/login";
            } else if (usersService.findUserByUsername(users.getUsername()) != null) {
                return "redirect:/signup?error=usernameExists";
            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in user signup");
            return "/authentication/signup";
        }
    }

    @RequestMapping("/findAllUsers.do")
    public String findAllUsers(HttpServletRequest request) {
        request.getSession().setAttribute("usersList", usersService.findAll());
        return "allUsersPage";
    }

    @RequestMapping("/saveUserInfo.do")
    public String saveUserInfo(@ModelAttribute UserInfo userInfo, @RequestParam("userId") String userId, @RequestParam("userInfoId") String userInfoId) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        //
        UserInfo oldUserInfo = userInfoService.getById(Long.parseLong(userInfoId));
        //
        if (!userInfo.getName().equals("") && !userInfo.getFamily().equals("")) {
            if (authenticatedUser.getUserInfo().getId() == Long.parseLong(userInfoId) && userInfoService.getById(Long.parseLong(userInfoId)) != null) {
                //
                if (userInfo.getProfilePictureFile() == null) {
                    userInfo.setProfilePictureFileAddress(oldUserInfo.getProfilePictureFileAddress());
                }
                //
                if (userInfo.getProfileBackgroundPictureFile() == null) {
                    userInfo.setProfileBackgroundPictureFileAddress(oldUserInfo.getProfileBackgroundPictureFileAddress());
                }
                //
                userInfo.setId(Long.parseLong(userInfoId));
                userInfoService.save(userInfo);
                //
            }
        }
        return "redirect:/user/UserInfo";
    }

}
