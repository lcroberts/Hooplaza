package com.csc340.Hooplaza.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping({"", "/"})
    public String home() {
        return "admin/community-requests";
    }

    @GetMapping("/requests")
    public String communityRequests() {
        return "admin/community-requests";
    }
}
