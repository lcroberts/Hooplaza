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
    public List<Community> getAllCommunities(String keyword) {
        return repo.search(keyword);
    }

    public Community getById(long commId) {
        return repo.getReferenceById(commId);
    }

    public void updateCommunity(Community community) {
        Community existing = repo.getReferenceById(community.getCommunityId());
        if (community.getName() != null) {
            existing.setName(community.getName());
        }
        if (community.getDescription() != null) {
            existing.setDescription(community.getDescription());
        }
        if (community.getLocationId() != null) {
            existing.setLocationId(community.getLocationId());
        }
        if (community.getPosts() != null) {
            existing.setPosts(community.getPosts());
        }
        community.setCommunityActive(existing.isCommunityActive());
        repo.save(community);
    }
}
