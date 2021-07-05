package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Friendship;
import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship,Long> {
    Friendship save(Friendship friendship);

    Friendship getById(long id);

    List<Friendship> getAllByUser2(Users users);

    List<Friendship> getAllByUser1(Users users);

    List<Friendship> getAllByUser1OrUser2(Users user1,Users user2);

    Friendship deleteById(long id);
}
