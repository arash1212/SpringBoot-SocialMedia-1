package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Friendship;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FriendshipService {
    private FriendshipRepository friendshipRepository;
    private UsersService usersService;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UsersService usersService) {
        this.usersService = usersService;
        this.friendshipRepository = friendshipRepository;
    }

    public void save(long authenticatedUserId, long userId) {
        Users authenticatedUser = usersService.findUserById(authenticatedUserId);
        //user that we are sending request to
        Users user2 = usersService.findUserById(userId);
        //
        Friendship friendship = new Friendship().setRequestDate(new Date()).setUser1(authenticatedUser).setUser2(user2);
        //
        friendshipRepository.save(friendship);
    }

    /**
     * @param users
     * @return method returns a list of friend requests that created by this 'user'
     */
    public List<Friendship> getUserCreatedFriendRequest(Users users) {
        return friendshipRepository.getAllByUser1(users);
    }

    /**
     * @param users
     * @return method returns a list of friend requests that 'other' 'users' created for this user
     */
    public List<Friendship> getUserFriendRequests(Users users) {
        return friendshipRepository.getAllByUser2(users);
    }

    /**
     * @return returns list of requests that user created or created for user
     */
    public List<Friendship> getAllUserRelatedRequests(Users users) {
        return friendshipRepository.getAllByUser1OrUser2(users, users);
    }

    public void delete(Friendship friendship) {

        friendshipRepository.deleteById(friendship.getId());
    }

    public Friendship getById(long id) {
        return friendshipRepository.getById(id);
    }

    public void denyFriendRequest(Friendship friendship) {
        friendship.setDeniedDate(new Date());
        friendshipRepository.save(friendship);
    }

    public void acceptFriendRequest(Friendship friendship) {
        friendship.setApproveDate(new Date());
        friendshipRepository.save(friendship);
    }

    public Friendship getByUser1AndUser2(Users user1, Users user2) {
        return friendshipRepository.getByUser1AndUser2(user1, user2);
    }
}
