package com.csc340.Hooplaza.moderator;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/mod")
@Controller
public class ModeratorController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService commService;

    @GetMapping({"", "/", "/bookmarks"})
    public String menu(Model model) {
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
        return "mod/bookmarks";
    }

    @GetMapping({"/board"})
    public String board(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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
        return "mod/board";
    }

    @GetMapping("/mod/add/id={id}/community/commId={commId}")
    public String addModerator(@PathVariable long id, @PathVariable long commId, Model model) {
        User user = userService.getUser(id);
        user.getModeratorOf().add(commService.getById(commId));
        user.setRole("MOD");
        userService.updateUser(user);
        return "redirect:/mod/community/details/id=" + commId;
    }

    @GetMapping("/community/details/id={id}")
    public String communityDetails(@PathVariable long id, Model model) {
        Community community = commService.getById(id);
        model.addAttribute("community", community);
        List<User> mods = community.getMods();
        model.addAttribute("mods", mods);
        List<User> members = community.getMembers();
        for (User user : mods) {
            members.remove(user);
        }
        model.addAttribute("members", members);

        if (community.isCommunityActive()) {
            model.addAttribute("status", "This community is still active");
        } else {
            model.addAttribute("status", "This community is inactive");
        }
        return "mod/community-details";
    }

    @GetMapping("/communities")
    public String moderatedCommunities(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Community> communities = user.getModeratorOf();
        for (int i = 0; i < communities.size(); ) {
            Community community = communities.get(i);
            if (!community.isCommunityActive()) {
                communities.remove(community);
            } else {
                i++;
            }
        }
        model.addAttribute("communities", communities);
        return "mod/moderated-communities";
    }

    @PostMapping("/community/edit/description/id={commId}")
    public String changeDescription(Community community, @PathVariable long commId, Model model) {
        String description = community.getDescription();
        community = commService.getById(commId);
        community.setDescription(description);
        commService.updateCommunity(community);

        return "redirect:/mod/community/details/id=" + commId;
    }

    @GetMapping("/kick/user/id={userId}/commId={commId}")
    public String kickUser(@PathVariable long userId, @PathVariable long commId) {
        User user = userService.getUser(userId);
        Community community = commService.getById(commId);
        user.getCommunities().remove(community);
        userService.updateUser(user);

        return "redirect:/mod/community/details/id=" + commId;
    }
}
