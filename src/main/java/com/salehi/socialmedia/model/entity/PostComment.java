package com.salehi.socialmedia.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PostComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Users author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Post post;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Date dateAdded;

    public long getId() {
        return id;
    }

    public PostComment setId(long id) {
        this.id = id;
        return this;
    }

    public Users getAuthor() {
        return author;
    }

    public PostComment setAuthor(Users author) {
        this.author = author;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public PostComment setPost(Post post) {
        this.post = post;
        return this;
    }

    public String getText() {
        return text;
    }

    public PostComment setText(String text) {
        this.text = text;
        return this;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public PostComment setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }
}
