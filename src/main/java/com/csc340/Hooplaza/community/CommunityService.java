package com.csc340.Hooplaza.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommunityService {

    @Autowired
    CommunityRepository repo;

    public void saveCommunity(Community community) {
        repo.save(community);
    }

    /*public Community getCommunityByLocation(String location) {
        return repo.findByLocation(location).orElseThrow(() -> new NoSuchElementException());
    }*/
}
