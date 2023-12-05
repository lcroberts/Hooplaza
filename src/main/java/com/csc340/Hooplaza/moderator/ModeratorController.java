package com.csc340.Hooplaza.moderator;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.post.PostService;
import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private CommunityService communityService;

    @GetMapping({"", "/", "/bookmarks"})
    public String menu(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Community> allCommunities = communityService.getAllCommunities();
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        List<Post> posts = user.getBookmarks();
        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        model.addAttribute("allCommunities", allCommunities);

        return "mod/bookmarks";
    }

    @GetMapping({"/board"})
    public String board(Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getLastActiveCommunityId() != 0) {
            int communityId = user.getLastActiveCommunityId();
            return "redirect:/mod/board/id=" + communityId;
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
            List<Community> allCommunities = communityService.getAllCommunities();
            for (int i = 0; i < allCommunities.size(); ) {
                Community community = allCommunities.get(i);
                if (!community.isCommunityActive()) {
                    allCommunities.remove(community);
                } else {
                    i++;
                }
            }
            model.addAttribute("postList", posts);
            model.addAttribute("communityList", communityList);
            model.addAttribute("allCommunities", allCommunities);


            return "mod/board";
        }
    }

    @GetMapping("/board/id={communityId}")
    public String board(@PathVariable long communityId, Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setLastActiveCommunityId((int) communityId);
        userService.updateUser(user);
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
        List<Community> allCommunities = communityService.getAllCommunities();
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        Community currentCommunity = communityService.getById(communityId);

        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        model.addAttribute("currentCommunity", currentCommunity);
        model.addAttribute("allCommunities", allCommunities);

        return "mod/board";
    }

    @GetMapping("/mod/add/id={id}/community/commId={commId}")
    public String addModerator(@PathVariable long id, @PathVariable long commId, Model model) {
        User user = userService.getUser(id);
        user.getModeratorOf().add(communityService.getById(commId));
        user.setRole("MOD");
        userService.updateUser(user);
        return "redirect:/mod/community/details/id=" + commId;
    }

    @GetMapping("/community/details/id={id}")
    public String communityDetails(@PathVariable long id, Model model) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.updateUser(user);
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        List<Community> allCommunities = communityService.getAllCommunities();
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        model.addAttribute("communityList", communityList);
        model.addAttribute("allCommunities", allCommunities);


        Community community = communityService.getById(id);
        model.addAttribute("community", community);
        List<User> mods = community.getMods();
        model.addAttribute("mods", mods);
        List<User> members = community.getMembers();
        for (User userMods : mods) {
            members.remove(userMods);
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
        userService.updateUser(user);
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        List<Community> allCommunities = communityService.getAllCommunities();
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        model.addAttribute("communityList", communityList);
        model.addAttribute("allCommunities", allCommunities);


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
        community = communityService.getById(commId);
        community.setDescription(description);
        communityService.updateCommunity(community);

        return "redirect:/mod/community/details/id=" + commId;
    }

    @GetMapping("/kick/user/id={userId}/commId={commId}")
    public String kickUser(@PathVariable long userId, @PathVariable long commId) {
        User user = userService.getUser(userId);
        Community community = communityService.getById(commId);
        user.getCommunities().remove(community);
        userService.updateUser(user);

        return "redirect:/mod/community/details/id=" + commId;
    }

    @GetMapping("/communities/bsearch")
    public String searchCommunitiesB(Model model, @Param("keyword") String keyword) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (keyword == null) {
            return "redirect:/mod/bookmarks";
        }
        List<Community> allCommunities = communityService.getAllCommunities(keyword);
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        List<Post> posts = user.getBookmarks();
        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        model.addAttribute("allCommunities", allCommunities);
        return "mod/bookmarks";
    }

    @GetMapping("/communities/psearch")
    public String searchCommunitiesP(Model model, @Param("keyword") String keyword) {
        Community currentCommunity = null;
        List<Post> posts = null;
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getLastActiveCommunityId() != 0) {
            int communityId = user.getLastActiveCommunityId();
            posts = postService.getAllPosts(communityId);
            currentCommunity = communityService.getById(communityId);
        } else {
            posts = postService.getAllPosts();
        }
        List<Community> communityList = user.getCommunities();
        for (int i = 0; i < communityList.size(); ) {
            Community community = communityList.get(i);
            if (!community.isCommunityActive()) {
                communityList.remove(community);
            } else {
                i++;
            }
        }
        List<Community> allCommunities = communityService.getAllCommunities(keyword);
        for (int i = 0; i < allCommunities.size(); ) {
            Community community = allCommunities.get(i);
            if (!community.isCommunityActive()) {
                allCommunities.remove(community);
            } else {
                i++;
            }
        }
        if (currentCommunity != null) {
            model.addAttribute("currentCommunity", currentCommunity);
        }
        model.addAttribute("postList", posts);
        model.addAttribute("communityList", communityList);
        model.addAttribute("allCommunities", allCommunities);
        return "mod/board";
    }
}