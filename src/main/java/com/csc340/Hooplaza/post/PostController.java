package com.csc340.Hooplaza.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    private PostService service;

    @PostMapping("/create")
    public String createPost(Post post) {
        service.savePost(post);
        return ("user/board");
    }

}
