package com.csc340.Hooplaza;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping({"", "/home", "/", "hooplaza"})
    public String home() {
        return "login/index";
    }
}
