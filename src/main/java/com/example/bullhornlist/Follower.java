package com.example.bullhornlist;

import javax.persistence.*;

@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private String username;

    private String followerfollowing;

//    @OneToMany
//    @JoinColumn(name = "user_id")


    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;


//    @ManyToOne(fetch = FetchType.EAGER)
//    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
