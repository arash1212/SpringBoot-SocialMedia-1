package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.PostComment;
import com.salehi.socialmedia.model.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostCommentService {
    private PostCommentRepository postCommentRepository;
    private PostService postService;
    private Authentication authentication;
    private UsersService usersService;

    @Autowired
    public PostCommentService(PostCommentRepository postCommentRepository, PostService postService, UsersService usersService) {
        this.postCommentRepository = postCommentRepository;
        this.postService = postService;
        this.usersService = usersService;
    }

    public void save(PostComment postComment) {
        //
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        postComment.getPost().getPostCommentsList().add(postComment);
        postComment.setAuthor(usersService.findUserByUsername(authentication.getName()));
        postComment.setDateAdded(new Date());
        //
        postCommentRepository.save(postComment);
        //
        postCommentRepository.save(postComment);
    }
}
