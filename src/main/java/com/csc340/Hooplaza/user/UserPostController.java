package com.csc340.Hooplaza.user;

import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/post")
@Controller
public class UserPostController {

    @Autowired
    private PostService service;

    @PostMapping("/create")
    public String createPost(Post post) {
        service.savePost(post);
        return "redirect:/user";
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
