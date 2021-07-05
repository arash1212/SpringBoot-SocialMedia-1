package com.salehi.socialmedia;

import com.salehi.socialmedia.model.service.FriendshipService;
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
    private FriendshipService friendshipService;

    Authentication authentication;

    @Autowired
    public MainController(PostService postService, UsersService usersService, FriendshipService friendshipService) {
        this.postService = postService;
        this.usersService = usersService;
        this.friendshipService = friendshipService;
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
     * @param request is used to store 'userPosts' and 'userDetail' attributes on session
     * @param id      is used to get requested user and it's posts from the datasource
     * @return method dispatches user to /user/profile html page with created session attributes (that used in the page to show user details and posts)
     */
    @RequestMapping("/user/profile")
    public String goToProfile(HttpServletRequest request, @RequestParam(name = "id", required = false) String id) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        if (id != null && !id.equals("")) {
            request.getSession().setAttribute("userPosts", postService.getUserPosts(usersService.findUserById(Long.parseLong(id))));
            request.getSession().setAttribute("userDetail", usersService.findUserById(Long.parseLong(id)));
            //get authenticated user's id (user that logged in)
            request.getSession().setAttribute("authenticatedUserId", usersService.findUserByUsername(authentication.getName()).getId());
        } else {
            request.getSession().setAttribute("userPosts", null);
            request.getSession().setAttribute("userDetail", null);
            request.getSession().setAttribute("authenticatedUserId", null);
        }
        return "/user/profile";
    }

    @RequestMapping("/user/friendRequest")
    public String goToUserFriendRequestsPage(HttpServletRequest request) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        request.getSession().setAttribute("userRelatedFriendRequestsList", friendshipService.getAllUserRelatedRequests(usersService.findUserByUsername(authentication.getName())));
        //
        return "/user/friendRequests";
    }

}
