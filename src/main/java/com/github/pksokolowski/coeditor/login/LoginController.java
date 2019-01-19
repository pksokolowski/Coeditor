package com.github.pksokolowski.coeditor.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = {"/login", "/"})
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLoginAttempt(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "password") String password,
            Model model) {

        if(!loginService.validateUser(name, password)){
            model.addAttribute("errorMessage", "incorrect credentials");
            return "login";
        }
        model.addAttribute("name", name);
        return "greeting";
    }
}
