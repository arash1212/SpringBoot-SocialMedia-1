package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.PostLikes;
import com.salehi.socialmedia.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    PostLikes save(PostLikes postLikes);

    PostLikes getByPostAndUsers(Post post, Users users);
}
