package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Friendship;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.FriendshipService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/user/")
public class FriendshipController {
    private FriendshipService friendshipService;
    private UsersService usersService;
    Authentication authentication;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, UsersService usersService) {
        this.friendshipService = friendshipService;
        this.usersService = usersService;
    }


    @RequestMapping("/sendFriendRequest.do")
    public String saveFriendRequest(@RequestParam("authenticatedUserID") String authenticatedUserId, @RequestParam("userId") String userId) {
        //
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //get authenticated user
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        //user who's trying to send a friend request
        Users user1 = usersService.findUserById(Long.parseLong(authenticatedUserId));
        //user who receives friend request
        Users user2 = usersService.findUserById(Long.parseLong(userId));
        //check if user already sent a request or not to send a new request And check if the user who's trying to send request is the user who's authenticated
        if (friendshipService.getByUser1AndUser2(user1, user2) == null && user1.getId() == authenticatedUser.getId()) {
            friendshipService.save(Long.parseLong(authenticatedUserId), Long.parseLong(userId));
        }
        return "redirect:/user/friendRequest";
    }

    /**
     * @param friendship the friend request that we are trying to remove
     * @return redirects to /user/friendRequest/ controller
     * this method removes a friend request if only the user that created the request asks for it
     */
    @RequestMapping("/RemoveFriendRequest.do")
    public String removeFriendRequest(@ModelAttribute Friendship friendship) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        //
        friendship = friendshipService.getById(friendship.getId());
        //check if user that's trying to remove a friend request is the sender of that request
        if (friendship != null && friendship.getUser1().getId() == authenticatedUser.getId())
            if (friendship.getDeniedDate() == null && friendship.getApproveDate() == null)
                friendshipService.delete(friendship);
        //
        return "redirect:/user/friendRequest";
    }

    @RequestMapping("/acceptFriendRequest.do")
    public String acceptFriendRequest(@ModelAttribute Friendship friendship) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        //
        friendship = friendshipService.getById(friendship.getId());
        //check if user that's trying to accept a friend request is the user that received that request
        if (friendship != null && friendship.getUser2().getId() == authenticatedUser.getId())
            if (friendship.getDeniedDate() == null )
                friendshipService.acceptFriendRequest(friendship);
        return "redirect:/user/friendRequest";
    }

    @RequestMapping("/denyFirendRequest.do")
    public String denyFriendRequest(@ModelAttribute Friendship friendship) {

        authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        //
        friendship = friendshipService.getById(friendship.getId());
        //check if user that's trying to deny a friend request is the user that received that request
        if (friendship != null && friendship.getUser2().getId() == authenticatedUser.getId())
            if (friendship.getApproveDate() == null)
                friendshipService.denyFriendRequest(friendship);
        //

        return "redirect:/user/friendRequest";
    }
}
