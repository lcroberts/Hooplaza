package com.csc340.Hooplaza.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /*Get all posts from a specific community
    public List<Post> getAllPosts(String cID) {
        if (cID != null) {
            return repo.search(cID);
        }
        return repo.findAll();
    } */
}
