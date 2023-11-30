package com.csc340.Hooplaza.admin;

import com.csc340.Hooplaza.CommunityRequest.CommunityRequest;
import com.csc340.Hooplaza.CommunityRequest.CommunityRequestService;
import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    CommunityRequestService commRequestService;

    @Autowired
    CommunityService commService;

    @Autowired
    UserService userService;

    @GetMapping({"", "/"})
    public String home() {
        return "redirect:admin/requests";
    }

    @GetMapping("/requests")
    public String communityRequests(Model model) {
        model.addAttribute("requestList", commRequestService.getAllRequests());
        return "admin/community-requests";
    }

    @GetMapping("/requests/accept/id={requestId}")
    public String acceptRequest(@PathVariable long requestId, Model model) {
        CommunityRequest request = commRequestService.getById(requestId);
        Community community = new Community();
        User requester = userService.getUser(request.getUserId());

        community.setName(request.getName());
        community.setLocationId(request.getLocationId());
        community.setDescription("");
        community.setPosts(new ArrayList<Post>());

        ArrayList<User> mod = new ArrayList<>();
        mod.add(requester);
        community.setMods(mod);

        ArrayList<User> users = new ArrayList<>();
        users.add(requester);
        community.setMembers(users);

        commService.saveCommunity(community);
        commRequestService.deleteById(requestId);

        return "redirect:/admin/requests";
    }

    @GetMapping("/requests/deny/id={requestId}")
    public String denyRequest(@PathVariable long requestId, Model model) {
        commRequestService.deleteById(requestId);
        return "redirect:/admin/requests";
    }

    @GetMapping("/communities")
    public String communities() {
        return "admin/communities";
    }

    @GetMapping("/user/details/id={userId}")
    public String userDetails(@PathVariable long userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "admin/user-details.html";
    }
}
