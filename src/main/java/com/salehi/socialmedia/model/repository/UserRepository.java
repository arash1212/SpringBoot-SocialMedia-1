package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users save(Users users);
    Users findUsersByUsername(String username);
}
