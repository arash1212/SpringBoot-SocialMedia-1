package com.salehi.socialmedia.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "users")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean enabled = true;

    public long getId() {
        return id;
    }

    public Users setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Users setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Users setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
