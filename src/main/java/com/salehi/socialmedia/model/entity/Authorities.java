package com.salehi.socialmedia.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "authorities")
public class Authorities implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String authority;

    public long getId() {
        return id;
    }

    public Authorities setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Authorities setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public Authorities setAuthority(String authority) {
        this.authority = authority;
        return this;
    }
}
