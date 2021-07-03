package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.PostService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class PostController {
    private PostService postService;
    private UsersService usersService;
    //
    private Authentication authentication;


    @Autowired
    public PostController(PostService postService, UsersService usersService) {
        this.postService = postService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/sendPost.do", method = RequestMethod.POST)
    public String sendPost(@ModelAttribute Post post) {
        try {
            postService.save(post);
            return "redirect:getUserPosts.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:getUserPosts.do";
        }
    }

    @RequestMapping("/getUserPosts.do")
    public String getUserPosts(Model model, HttpServletRequest request) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        System.out.println("authenticated username:"+authentication.getName());
        Users users = usersService.findUserByUsername(authentication.getName());
        //
        request.getSession().setAttribute("userPosts",postService.getUserPosts(users));
//        model.addAttribute("userPosts", postService.getUserPosts(users));
        return "redirect:/user/profile";
    }


}
