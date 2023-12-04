package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/community")
@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private PostService postService;

    @GetMapping()
    public String getCommunity(Model model) {
        List<Community> communities = communityService.getAllCommunities();
                model.addAttribute("communityList", communities);
        return "user/board";
    }

    @GetMapping("/id={communityId}")
    public String getCommunity(@PathVariable long communityId, Model model) {
        Community community = communityService.getById(communityId);
        model.addAttribute("community", community);
        return "user/board";
    }
    @GetMapping({"", "/", "/board"})
    public String menu(long communityId, Model model) {
        List<Post> posts = postService.getAllPosts(communityId);
        model.addAttribute("postList", posts);
        return "user/board";
    }
}
