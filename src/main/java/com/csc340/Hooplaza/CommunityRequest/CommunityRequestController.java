package com.csc340.Hooplaza.CommunityRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/request")
@Controller
public class CommunityRequestController {

    @Autowired
    private CommunityRequestService service;

    @PostMapping("/create")
    public String createRequest(CommunityRequest request) {
        service.saveCommunityRequest(request);
        return ("redirect:/user/board");
    }
}
