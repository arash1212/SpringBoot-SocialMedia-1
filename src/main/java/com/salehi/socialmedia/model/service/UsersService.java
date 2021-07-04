package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Authorities;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthoritiesService authoritiesService;

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

    public List<Users> findAll() {
        return userRepository.findAll();
    }
}
