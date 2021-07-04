package com.salehi.socialmedia.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "postLikes")
public class PostLikes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users users;

    public long getId() {
        return id;
    }

    public PostLikes setId(long id) {
        this.id = id;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public PostLikes setPost(Post post) {
        this.post = post;
        return this;
    }

    public Users getUsers() {
        return users;
    }

    public PostLikes setUsers(Users users) {
        this.users = users;
        return this;
    }
}
