package com.csc340.Hooplaza.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
    // redirect goes to its matching java method,
    // return goes to matching html file

    @Autowired
    private UserService service;
    

}
