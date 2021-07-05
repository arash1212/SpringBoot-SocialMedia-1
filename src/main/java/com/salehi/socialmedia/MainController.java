package com.salehi.socialmedia;

import com.salehi.socialmedia.model.entity.Friendship;
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
import java.util.List;

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
     * @return method dispatches user to /user/profile html page with created session attributes (that will be used in the page to show user details and posts and etc)
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
            //
            request.getSession().setAttribute("userRelatedFriendRequestsList", friendshipService.getAllUserRelatedRequests(usersService.findUserByUsername(authentication.getName())));
            //check if user has any friend request to or received any friend request from the id that we are trying to dispatch to it's profile
            List<Friendship> friendRequestsList = friendshipService.getAllUserRelatedRequests(usersService.findUserByUsername(authentication.getName()));
            for (Friendship friendship : friendRequestsList) {
                if (friendship.getUser2().getId() == Long.parseLong(id)) {
                    request.getSession().setAttribute("friendRequestState","requestSender");
                    return "/user/profile";
                }else if(friendship.getUser1().getId() == Long.parseLong(id)){
                    request.getSession().setAttribute("friendRequestState","requestReceiver");
                    return "/user/profile";
                }
            }

        } else {
            request.getSession().setAttribute("userPosts", null);
            request.getSession().setAttribute("userDetail", null);
            request.getSession().setAttribute("authenticatedUserId", null);
            request.getSession().setAttribute("friendRequestState","none");
        }
        request.getSession().setAttribute("friendRequestState","none");
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
