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

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }

    public Post getPost(long postId) {
        return postRepository.getReferenceById(postId);
    }

    /*Get all posts from a specific community (could use for bookmarks with some tweaks)
    public List<Post> getAllPosts(String communityId) {
        if (communityId != null) {
            return postRepository.search(communityId);
        }
        return postRepository.findAll();
    } */
}
