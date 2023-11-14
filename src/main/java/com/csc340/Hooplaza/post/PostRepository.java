package com.csc340.Hooplaza.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /*Get all posts from a specific community

    @Query("SELECT p FROM Post p WHERE CONCAT(p.cID, p.type) LIKE %?1%")
    public List<Product> search(String keyword); */
}
