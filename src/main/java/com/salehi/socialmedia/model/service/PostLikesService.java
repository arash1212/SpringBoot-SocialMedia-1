package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.PostLikes;
import com.salehi.socialmedia.model.repository.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikesService {
    private PostLikesRepository postLikesRepository;

    @Autowired
    public PostLikesService(PostLikesRepository postLikesRepository){
        this.postLikesRepository=postLikesRepository;
    }

    public void save(PostLikes postLikes){
        postLikesRepository.save(postLikes);
    }
}
