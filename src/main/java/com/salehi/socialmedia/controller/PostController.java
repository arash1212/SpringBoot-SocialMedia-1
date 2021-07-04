package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.PostService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     *
     * @param post gets the request parameters and creates a post object to save in database (with postService)
     * @param id gets userId parameter from html form and sends it to 'getUserPosts.do' controller
     * @return method redirects user to 'getUserPosts.do' controller
     */
    @RequestMapping(value = "/sendPost.do")
    public String sendPost(@ModelAttribute Post post, @RequestParam("userId") String id) {
        try {
            postService.save(post);
            return "redirect:/user/getUserPosts.do?userId=" + id;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/user/getUserPosts.do?userId=" + id;
        }
    }

    /**
     * this method is only used to get to authenticated user's profile
     *
     * @param request used to store user posts on session
     * @return method redirects to /user/profile/ controller
     */
    @RequestMapping("/getMyPosts.do")
    public String getMyPosts(HttpServletRequest request) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        Users users = usersService.findUserByUsername(authentication.getName());
        //
        request.getSession().setAttribute("userPosts", postService.getUserPosts(users));
        return "redirect:/user/profile?id=" + users.getId();
    }

    /**
     * this method was used to get posts from all the users,and now it just adds a userId parameter to the request
     * to fetch selected user posts and details to show on profile (this part is done in/user/profile/' controller in  main controller class
     *
     * @param id used to send user id to '/user/profile/' controller (in  main controller class)
     * @return method redirects user to /user/profile controller
     */
    @RequestMapping(value = "/getUserPosts.do")
    public String getUserPosts(@RequestParam("userId") String id) {
        Users users = usersService.findUserById(Long.parseLong(id));
        //
        return "redirect:/user/profile?id=" + users.getId();
    }


}
