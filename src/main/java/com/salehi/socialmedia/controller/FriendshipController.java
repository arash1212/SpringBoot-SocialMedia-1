package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Friendship;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.service.FriendshipService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public FriendshipController(FriendshipService friendshipService, UsersService usersService) {
        this.friendshipService = friendshipService;
        this.usersService = usersService;
    }


    @RequestMapping("/sendFriendRequest.do")
    public String saveFriendRequest(@RequestParam("authenticatedUserID") String authenticatedUserId, @RequestParam("userId") String userId) {
        friendshipService.save(Long.parseLong(authenticatedUserId), Long.parseLong(userId));
        return "redirect:/user/friendRequest";
    }

    /**
     *
     * @param friendship the friend request that we are trying to remove
     * @return redirects to /user/friendRequest/ controller
     *
     * this method removes a friend request if only the user that created the request asks for it
     */
    @RequestMapping("/RemoveFriendRequest.do")
    public String removeFriendRequest(@ModelAttribute Friendship friendship){
        friendship = friendshipService.getById(friendship.getId());
        //
        friendshipService.delete(friendship);
        //
        return "redirect:/user/friendRequest";
    }

    @RequestMapping("/acceptFriendRequest.do")
    public String acceptFriendRequest(@ModelAttribute Friendship friendship){
        friendship = friendshipService.getById(friendship.getId());
        //
        friendshipService.acceptFriendRequest(friendship);
        return "redirect:/user/friendRequest";
    }

    @RequestMapping("/denyFirendRequest.do")
    public String denyFriendRequest(@ModelAttribute Friendship friendship){
        friendship = friendshipService.getById(friendship.getId());
        //
        friendshipService.denyFriendRequest(friendship);
        //
        return "redirect:/user/friendRequest";
    }
}
