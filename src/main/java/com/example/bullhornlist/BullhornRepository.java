package com.example.bullhornlist;


import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface BullhornRepository extends CrudRepository<Bullhorn, Long> {

    ArrayList<Bullhorn> findByUsername(String username);

}
