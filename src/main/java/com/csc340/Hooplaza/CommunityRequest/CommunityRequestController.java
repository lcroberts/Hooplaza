package com.csc340.Hooplaza.CommunityRequest;

import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/request")
@Controller
public class CommunityRequestController {

    @Autowired
    private CommunityRequestService service;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createRequest(CommunityRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        request.setUserId(user.getUserId());
        request.setUserName(user.getName());

        service.saveCommunityRequest(request);
        return ("redirect:/user/board");
    }
}
