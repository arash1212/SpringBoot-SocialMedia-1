package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsersService extends JpaRepository<Users, Long> {
    Users save(Users users);
}
