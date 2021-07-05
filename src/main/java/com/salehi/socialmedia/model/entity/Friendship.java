package com.salehi.socialmedia.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "friendship")
public class Friendship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date requestDate;
    private Date approveDate;
    private Date deniedDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Users user1;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Users user2;

    public long getId() {
        return id;
    }

    public Friendship setId(long id) {
        this.id = id;
        return this;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Friendship setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public Friendship setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
        return this;
    }

    public Date getDeniedDate() {
        return deniedDate;
    }

    public Friendship setDeniedDate(Date deniedDate) {
        this.deniedDate = deniedDate;
        return this;
    }

    public Users getUser1() {
        return user1;
    }

    public Friendship setUser1(Users user1) {
        this.user1 = user1;
        return this;
    }

    public Users getUser2() {
        return user2;
    }

    public Friendship setUser2(Users user2) {
        this.user2 = user2;
        return this;
    }
}
