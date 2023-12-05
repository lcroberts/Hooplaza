package com.csc340.Hooplaza;

import com.csc340.Hooplaza.user.User;
import com.csc340.Hooplaza.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping({"", "/home", "/", "hooplaza"})
    public String home(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", name);
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup/create")
    public String signupCreate(User user) {
        user.setRole("USER");
        userService.saveNewUser(user);
        return "redirect:/redirect-user";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/403")
    public String _403() {
        return "index";
    }

    @GetMapping("/redirect-user")
    public String redirectUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            email = authentication.getName();
        } else {
            return "redirect:/";
        }
        User user = userService.getUserByEmail(email);

        if (!user.isActive()) {
            return "banned";
        }

        return switch (user.getRole()) {
            case "USER" -> "redirect:/user";
            case "MOD" -> "redirect:/mod";
            case "ADMIN" -> "redirect:/admin";
            default -> "redirect:/";
        };
    }
}
