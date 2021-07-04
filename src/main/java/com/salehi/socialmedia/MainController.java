package com.salehi.socialmedia;

import com.salehi.socialmedia.model.service.PostService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller for dispatching to basic pages like login ,index or signup page
 */
@Controller
public class MainController {
    private PostService postService;
    private UsersService usersService;

    Authentication authentication;

    @Autowired
    public MainController(PostService postService, UsersService usersService) {
        this.postService = postService;
        this.usersService = usersService;
    }

    @RequestMapping("/")
    public String goToIndex() {
        return "index";
    }

    @RequestMapping("/login")
    public String goToLoginPage() {
        return "/authentication/login";
    }

    @RequestMapping("/signup")
    public String goToSignUpPage() {
        return "/authentication/signup";
    }

    /**
     *
     * @param request is used to store 'userPosts' and 'userDetail' attributes on session
     * @param id is used to get requested user and it's posts from the datasource
     * @return method dispatches user to /user/profile html page with created session attributes (that used in the page to show user details and posts)
     */
    @RequestMapping("/user/profile")
    public String goToProfile(HttpServletRequest request, @RequestParam(name = "id", required = false) String id) {
        if (id != null && !id.equals("")) {
            request.getSession().setAttribute("userPosts", postService.getUserPosts(usersService.findUserById(Long.parseLong(id))));
            request.getSession().setAttribute("userDetail", usersService.findUserById(Long.parseLong(id)));
        } else {
            request.getSession().setAttribute("userPosts", null);
            request.getSession().setAttribute("userDetail", null);
        }
        return "/user/profile";
    }

}
