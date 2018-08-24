package com.example.bullhornlist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private String username;

    private String followerfollowing;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFollowerfollowing() {
        return followerfollowing;
    }

    public void setFollowerfollowing(String followerfollowing) {
        this.followerfollowing = followerfollowing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
