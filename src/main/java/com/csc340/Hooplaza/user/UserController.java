package com.csc340.Hooplaza.user;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    // redirect goes to its matching java method,
    // return goes to matching html file
    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    //private String key = System.getenv("MAPS_KEY");

    @GetMapping({"", "/", "/bookmarks"})
    public String bookmarks(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        List<Post> posts = user.getBookmarks();
        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        return "user/bookmarks";
    }

    @GetMapping({"/board"})
    public String board(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getLastActiveCommunityId() != 0) {
            int communityId = user.getLastActiveCommunityId();
            return "redirect:/board/id={communityId}";
        } else {
            List<Post> posts = postService.getAllPosts();
            List<Community> communityList = user.getCommunities();
            for (int i = 0; i < communityList.size(); ) {
                Community community = communityList.get(i);
                if (!community.isCommunityActive()) {
                    communityList.remove(community);
                } else {
                    i++;
                }
            }
            model.addAttribute("postList", posts);
            model.addAttribute("communityList", communityList);
            return "user/board";
        }
    }

    @GetMapping("/board/id={communityId}")
    public String board(@PathVariable long communityId, Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setLastActiveCommunityId((int)communityId);
        List<Post> posts = postService.getAllPosts(communityId);
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        Community currentCommunity = communityService.getById(communityId);

        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        model.addAttribute("currentCommunity", currentCommunity);
        return "user/board";
    }
}
