package com.csc340.Hooplaza.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

    @Autowired
    CommunityRepository repo;

    public void saveCommunity(Community community) {
        repo.save(community);
    }
}
