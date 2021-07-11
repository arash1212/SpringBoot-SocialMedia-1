package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Friendship;
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

    @Query("select entity from users as entity left join fetch entity.userInfo where entity.id=:id")
    Users findUserByIdWithUserInfo(long id);

    @Query("select entity from friendship as entity where entity.user1=:authenticatedUser or entity.user2=:authenticatedUser and entity.approveDate is not null")
    List<Friendship> getUserAcceptedFriendRequests(Users authenticatedUser);
}
