package com.csc340.Hooplaza.user;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private CommunityService communityService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public String createPost(Post post) {
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        //we're gonna need a global(?) variable to determine which community were in. Or a dropdown menu?
        User user = userService.getUserByEmail(userAuthentication.getName());
        //get current community id
        post.setCommunityId(user.getLastActiveCommunityId());
        post.setUserId(user.getUserId());
        postService.savePost(post);
        return "redirect:/user/board";
    }

    @GetMapping("/delete/id={productId}")
    public String deletePost(@PathVariable long postId, Model model) {
        postService.deletePost(postId);
        return "redirect:/user/board";
    }

    @PostMapping("/update")
    public String updatePost(Post post) {
        postService.savePost(post);
        return "redirect:/user/board";
    }

    @GetMapping("/bookmark/id={postId}")
    public String bookmarkPost(@PathVariable long postId, Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = postService.getPost(postId);
        if (!user.getBookmarks().contains(post)) {
            user.getBookmarks().add(post);
            userService.updateUser(user);
        }
        return "redirect:/user/board";
    }
}
