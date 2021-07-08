package com.salehi.socialmedia.controller;

import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.PostComment;
import com.salehi.socialmedia.model.service.PostCommentService;
import com.salehi.socialmedia.model.service.PostService;
import com.salehi.socialmedia.model.service.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/postComment")
public class PostCommentController {

    private PostCommentService postCommentService;
    private PostService postService;
    private UsersService usersService;

    public PostCommentController(PostCommentService postCommentService, PostService postService, UsersService usersService) {
        this.postCommentService = postCommentService;
        this.postService = postService;
        this.usersService = usersService;
    }

    @RequestMapping("/saveComment.do")
    public String savePost(@ModelAttribute PostComment postComment, @RequestParam("postId") String postId, @RequestParam(value = "url") String url) {
        //
        Post post = postService.findById(Long.parseLong(postId));
        //
        postComment.setPost(post);
        //
        postCommentService.save(postComment);
        return "redirect:/user/profile?id="+url;
    }
}
