package com.csc340.Hooplaza.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/community")
@Controller
public class CommunityController {

    @Autowired
    private CommunityService service;

}
