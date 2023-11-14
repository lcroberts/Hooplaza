package com.csc340.Hooplaza.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;
    void savePost(Post post) {
        repo.save(post);
    }


    /*Get all posts from a specific community
    public List<Post> getAllPosts(String cID) {
        if (cID != null) {
            return repo.search(cID);
        }
        return repo.findAll();
    } */
}
