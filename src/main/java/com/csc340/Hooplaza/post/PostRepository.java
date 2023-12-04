package com.csc340.Hooplaza.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //Get all posts from a specific community
    @Query("SELECT p FROM Post p WHERE p.communityId = ?1 ORDER BY p.postId DESC")
    public List<Post> search(String keyword);
    public List<Post> findPostsByCommunityId(long communityId);
}
