package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Authorities;
import com.salehi.socialmedia.model.entity.Friendship;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthoritiesService authoritiesService;
    //
    Authentication authentication;

    @Autowired
    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthoritiesService authoritiesService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesService = authoritiesService;
    }

    public void save(Users users) {
        //encoding user inserted password before saving the new user
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        //
        users.setUsername(users.getUsername().toLowerCase());
        //saving user authority at signup (as user)
        Authorities authorities = new Authorities().setUsername(users.getUsername()).setAuthority("user");
        authoritiesService.save(authorities);
        //
        userRepository.save(users);
    }

    public Users findUserByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    public Users findUserById(long id) {
        return userRepository.findById(id);
    }

    public Users findUserByIdWithUserInfo(long id) {
        return userRepository.findUserByIdWithUserInfo(id);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public List<Users> getUserFriendsList() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        Users authenticatedUser = userRepository.findUsersByUsername(authentication.getName());
        //
        List<Users> friendList = new ArrayList<>();
        //get users from accepted friendRequests list (User1/User2) where their user id is not equal with authenticated user id(to get user's friend list)
        for (Friendship friendship : userRepository.getUserAcceptedFriendRequests(authenticatedUser)) {
            if (friendship.getUser1().getId() != authenticatedUser.getId()) {
                friendList.add(friendship.getUser1());
            } else if (friendship.getUser2().getId() != authenticatedUser.getId()) {
                friendList.add(friendship.getUser2());
            }
            //
        }
        //
        return friendList;
    }
}
