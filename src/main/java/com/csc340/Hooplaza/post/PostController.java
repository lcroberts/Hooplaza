package com.csc340.Hooplaza.post;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    private PostService service;

    @PostMapping("/create")
    public String createPost(Post post) {
        service.savePost(post);
        return "redirect:/user";
    }

    @GetMapping("/all")
    public String getAllPosts(Model model) {
        List<Post> posts = service.getAllPosts();
        model.addAttribute("postList", posts);
        return "user/board";
    }

    @GetMapping("/delete/id={productId}")
    public String deletePost(@PathVariable long postId, Model model) {
        service.deletePost(postId);
        return "redirect:/product/all";
    }

    @PostMapping("/update")
    public String updateProduct(Post post) {
        service.savePost(post);
        return "redirect:/user/board";
    }
}
