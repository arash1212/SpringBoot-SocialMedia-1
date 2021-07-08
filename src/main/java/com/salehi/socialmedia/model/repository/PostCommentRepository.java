package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    PostComment save(PostComment postComment);

    List<PostComment> getAllByPost(Post post);
}
