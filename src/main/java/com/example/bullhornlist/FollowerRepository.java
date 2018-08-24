package com.example.bullhornlist;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FollowerRepository extends CrudRepository<Follower, Long> {

    ArrayList<Follower> findByUsername(String username);



}
