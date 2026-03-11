package com.skillup.skillup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute("title", "SkillUp");
        return "home";
    }

    @GetMapping("/check")
    public String check(Model model) {
        return "login"; // Or just return a string if it's a @RestController, but this is @Controller
    }
}
