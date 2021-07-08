package com.salehi.socialmedia.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users author;
    @Column(nullable = false)
    private Date dateAdded;
    @Transient
    @JsonIgnore
    private MultipartFile filePosted;
    @Column(nullable = true)
    private String filePath;
    //Public , Friends , Just Me
    @Column(nullable = false)
    private String audience = "Public";
    private boolean postHaveImage;
    private boolean postHaveVideo;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<PostLikes> postLikes = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<PostComment> postCommentsList = new ArrayList<>();

    public List<PostComment> getPostCommentsList() {
        return postCommentsList;
    }

    public Post setPostCommentsList(List<PostComment> postComment) {
        this.postCommentsList = postComment;
        return this;
    }

    public List<PostLikes> getPostLikes() {
        return postLikes;
    }

    public Post setPostLikes(List<PostLikes> postLikes) {
        this.postLikes = postLikes;
        return this;
    }

    public boolean isPostHaveImage() {
        return postHaveImage;
    }

    public Post setPostHaveImage(boolean postHaveImage) {
        this.postHaveImage = postHaveImage;
        return this;
    }

    public boolean isPostHaveVideo() {
        return postHaveVideo;
    }

    public Post setPostHaveVideo(boolean postHaveVideo) {
        this.postHaveVideo = postHaveVideo;
        return this;
    }

    public String getAudience() {
        return audience;
    }

    public Post setAudience(String audience) {
        this.audience = audience;
        return this;
    }

    public long getId() {
        return id;
    }

    public Post setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Post setText(String text) {
        this.text = text;
        return this;
    }

    public Users getAuthor() {
        return author;
    }

    public Post setAuthor(Users author) {
        this.author = author;
        return this;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Post setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public MultipartFile getFilePosted() {
        return filePosted;
    }

    public Post setFilePosted(MultipartFile filePosted) {
        this.filePosted = filePosted;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Post setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
