package com.csc340.Hooplaza.user;

import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    // redirect goes to its matching java method,
    // return goes to matching html file
    @Autowired
    private PostService postService;

    @Autowired
    private UserService service;


    //private String key = System.getenv("MAPS_KEY");

    @GetMapping({"", "/", "/board"})
    public String menu(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("postList", posts);
        return "user/board";
    }


}
