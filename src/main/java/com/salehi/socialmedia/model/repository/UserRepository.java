package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users save(Users users);

    Users findById(long id);

    Users findUsersByUsername(String username);

    @Query("select entity from users as entity")
    List<Users> findAll();
}
