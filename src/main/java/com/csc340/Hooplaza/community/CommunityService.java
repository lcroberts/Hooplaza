package com.csc340.Hooplaza.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    CommunityRepository repo;

    public void saveCommunity(Community community) {
        repo.save(community);
    }

    public List<Community> getAllCommunities() {
        return repo.findAll();
    }

    public Community getById(long commId) {
        return repo.getReferenceById(commId);
    }
}
