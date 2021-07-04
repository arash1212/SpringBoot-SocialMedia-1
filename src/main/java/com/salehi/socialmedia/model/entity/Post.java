package com.salehi.socialmedia.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
