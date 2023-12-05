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
        for (int i = 0; i < requests.size(); ) {
            CommunityRequest request = requests.get(i);
            try {
                User requester = userService.getUser(request.getUserId());

                if (!requester.isActive()) {
                    requests.remove(request);
                    commRequestService.deleteById(request.getRequestId());
                } else if (!request.isRequestActive()) {
                    requests.remove(request);
                } else {
                    i++;
                }
            } catch (Exception e) {
                requests.remove(request);
                commRequestService.deleteById(request.getRequestId());
            }
        }
        model.addAttribute("requestList", requests);
        return "admin/community-requests";
    }

    @GetMapping("/requests/processed")
    public String processedCommunityRequests(Model model) {
        List<CommunityRequest> requests = commRequestService.getAllRequests();
        for (int i = 0; i < requests.size(); ) {
            CommunityRequest request = requests.get(i);
            try {
                User requester = userService.getUser(request.getUserId());
                if (request.isRequestActive()) {
                    requests.remove(request);
                } else {
                    i++;
                }
            } catch (Exception e) {
                requests.remove(request);
                commRequestService.deleteById(request.getRequestId());
            }
        }
        model.addAttribute("requestList", requests);

        return "admin/community-requests-processed";
    }

    @GetMapping("/requests/accept/id={requestId}")
    public String acceptRequest(@PathVariable long requestId, Model model) {
        CommunityRequest request = commRequestService.getById(requestId);
        request.setRequestAccepted(true);
        request.setRequestActive(false);

        Community community = new Community();
        User requester = userService.getUser(request.getUserId());
        community.setName(request.getName());
        community.setLocationId(request.getLocationId());
        community.setDescription("");

        requester.setRole("MOD");
        requester.getModeratorOf().add(community);
        requester.getCommunities().add(community);

        commService.saveCommunity(community);
        commRequestService.updateRequest(request);

        return "redirect:/admin/requests";
    }

    @GetMapping("/requests/deny/id={requestId}")
    public String denyRequest(@PathVariable long requestId, Model model) {
        CommunityRequest request = commRequestService.getById(requestId);
        request.setRequestActive(false);
        commRequestService.updateRequest(request);
        return "redirect:/admin/requests";
    }

    @GetMapping("/communities")
    public String communities(Model model) {
        List<Community> communities = commService.getAllCommunities();
        for (int i = 0; i < communities.size(); ) {
            Community community = communities.get(i);
            if (!community.isCommunityActive()) {
                communities.remove(community);
            } else {
                i++;
            }
        }
        model.addAttribute("communities", communities);
        return "admin/communities";
    }

    @GetMapping("/communities/removed")
    public String removedCommunities(Model model) {
        List<Community> communities = commService.getAllCommunities();
        for (int i = 0; i < communities.size(); ) {
            Community community = communities.get(i);
            if (community.isCommunityActive()) {
                communities.remove(community);
            } else {
                i++;
            }
        }
        model.addAttribute("communities", communities);
        return "admin/communities-removed";
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

        if (community.isCommunityActive()) {
            model.addAttribute("status", "This community is still active");
        } else {
            model.addAttribute("status", "This community is inactive");
        }
        return "admin/community-details";
    }

    @GetMapping("/community/remove/id={id}")
    public String removeCommunity(@PathVariable long id, Model model) {
        Community community = commService.getById(id);
        community.setCommunityActive(false);
        commService.updateCommunity(community);

        return "redirect:/admin/community/details/id=" + id;
    }

    @GetMapping("/community/activate/id={id}")
    public String activateCommunity(@PathVariable long id, Model model) {
        Community community = commService.getById(id);
        community.setCommunityActive(true);
        commService.updateCommunity(community);

        return "redirect:/admin/community/details/id=" + id;
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
        user.getModeratorOf().add(commService.getById(commId));
        user.setRole("MOD");
        userService.updateUser(user);
        return "redirect:/admin/community/details/id=" + commId;
    }

    @GetMapping("/mod/remove/id={id}/community/commId={commId}")
    public String removeModerator(@PathVariable long id, @PathVariable long commId, Model model) {
        User user = userService.getUser(id);
        user.getModeratorOf().remove(commService.getById(commId));
        if (user.getModeratorOf().isEmpty() || user.getModeratorOf() == null) {
            user.setRole("USER");
            userService.updateUser(user);
        }
        return "redirect:/admin/community/details/id=" + commId;
    }
}
