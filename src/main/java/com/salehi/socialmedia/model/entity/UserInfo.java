package com.salehi.socialmedia.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "userInfo")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String family;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String job;
    @Transient
    @JsonIgnore
    private MultipartFile profilePictureFile;
    @Transient
    @JsonIgnore
    private MultipartFile profileBackgroundPictureFile;
    @Column
    private String profilePictureFileAddress;
    @Column
    private String profileBackgroundPictureFileAddress;

    public long getId() {
        return id;
    }

    public UserInfo setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public UserInfo setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserInfo setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getJob() {
        return job;
    }

    public UserInfo setJob(String job) {
        this.job = job;
        return this;
    }

    public MultipartFile getProfilePictureFile() {
        return profilePictureFile;
    }

    public UserInfo setProfilePictureFile(MultipartFile profilePictureFile) {
        this.profilePictureFile = profilePictureFile;
        return this;
    }

    public MultipartFile getProfileBackgroundPictureFile() {
        return profileBackgroundPictureFile;
    }

    public UserInfo setProfileBackgroundPictureFile(MultipartFile profileBackgroundPictureFile) {
        this.profileBackgroundPictureFile = profileBackgroundPictureFile;
        return this;
    }

    public String getProfilePictureFileAddress() {
        return profilePictureFileAddress;
    }

    public UserInfo setProfilePictureFileAddress(String profilePictureFileAddress) {
        this.profilePictureFileAddress = profilePictureFileAddress;
        return this;
    }

    public String getProfileBackgroundPictureFileAddress() {
        return profileBackgroundPictureFileAddress;
    }

    public UserInfo setProfileBackgroundPictureFileAddress(String profileBackgroundPictureFileAddress) {
        this.profileBackgroundPictureFileAddress = profileBackgroundPictureFileAddress;
        return this;
    }
}
