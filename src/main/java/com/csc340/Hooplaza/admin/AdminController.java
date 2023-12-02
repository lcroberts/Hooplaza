package com.csc340.Hooplaza.admin;

import com.csc340.Hooplaza.CommunityRequest.CommunityRequest;
import com.csc340.Hooplaza.CommunityRequest.CommunityRequestService;
import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.community.CommunityService;
import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        List<CommunityRequest> requests = commRequestService.getAllRequests();
        for (int i = 0; i < requests.size(); i++) {
            CommunityRequest request = requests.get(i);
            try {
                User requester = userService.getUser(request.getUserId());

                if (!requester.isActive()) {
                    requests.remove(request);
                    commRequestService.deleteById(request.getRequestId());
                }
            } catch (Exception e) {
                requests.remove(request);
                commRequestService.deleteById(request.getRequestId());
            }
        }
        model.addAttribute("requestList", requests);
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

//        community.getMods().add(requester);
        requester.setRole("MOD");
        requester.getModeratorOf().add(community);
        requester.getCommunities().add(community);

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
    public String communities(Model model) {
        model.addAttribute("communities", commService.getAllCommunities());
        return "admin/communities";
    }

    @GetMapping("/community/details/id={communityId}")
    public String communityDetails(@PathVariable long communityId, Model model) {
        Community community = commService.getById(communityId);
        model.addAttribute("community", community);
        List<User> mods = community.getMods();
        model.addAttribute("mods", mods);
        List<User> members = community.getMembers();
        for (User user : mods) {
            members.remove(user);
        }
        model.addAttribute("members", members);
        return "admin/community-details";
    }

    @GetMapping("/user/details/id={userId}")
    public String userDetails(@PathVariable long userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        if (userService.getUser(userId).isActive()) {
            model.addAttribute("userStatus", "This user is active");
        } else {
            model.addAttribute("userStatus", "This user has been suspended");
        }

        return "admin/user-details.html";
    }

    @GetMapping("/user/ban/id={userId}")
    public String banUser(@PathVariable long userId, Model model) {
        User user = userService.getUser(userId);
        user.setActive(false);
        userService.updateUser(user);
        return "redirect:/admin/user/details/id=" + userId;
    }

    @GetMapping("/user/unban/id={userId}")
    public String unbanUser(@PathVariable long userId, Model model) {
        User user = userService.getUser(userId);
        user.setActive(true);
        userService.updateUser(user);
        return "redirect:/admin/user/details/id=" + userId;
    }

    @GetMapping("/mod/add/id={id}/community/commId={commId}")
    public String addModerator(@PathVariable long id, @PathVariable long commId, Model model) {
        User user = userService.getUser(id);
        user.getModeratorOf().add(commService.getById(id));
        userService.updateUser(user);
        return "redirect:/admin/community/details/id=" + commId;
    }

    @GetMapping("/mod/remove/id={id}/community/commId={commId}")
    public String removeModerator(@PathVariable long id, @PathVariable long commId, Model model) {
        User user = userService.getUser(id);
        user.getModeratorOf().remove(commService.getById(commId));
        userService.updateUser(user);
        return "redirect:/admin/community/details/id=" + commId;
    }
}
