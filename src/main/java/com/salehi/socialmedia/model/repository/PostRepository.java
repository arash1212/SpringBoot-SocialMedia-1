package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Post save(Post post);

    Post findById(long id);

    List<Post> getAllByAuthor(Users Author);

}
