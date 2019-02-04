package com.github.pksokolowski.coeditor.register;

import com.github.pksokolowski.coeditor.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private RegistrationService registrationService;
    private InvitationValidatorService invitationValidatorService;

    public RegisterController(RegistrationService registrationService, InvitationValidatorService invitationValidatorService) {
        this.registrationService = registrationService;
        this.invitationValidatorService = invitationValidatorService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(
            @RequestParam(name = "invitationCode") Long invitationCode,
            Model model) {
        if (!invitationValidatorService.isValid(invitationCode)) {
            return "no_permission";
        }

        model.addAttribute("invitationCode", invitationCode);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam Long invitationCode,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String passwordRepeated,
            Model model) {

        if (!invitationValidatorService.isValid(invitationCode)) {
            return "no_permission";
        }

        if (!password.equals(passwordRepeated)) {
            model.addAttribute("errorMessage", "Password repeated incorrectly");
            return "register";
        }

        var user = new User(name, password);
        var errorCode = registrationService.register(user, invitationCode);

        if (errorCode > 0) {
            model.addAttribute("errorMessage", "error registering user");
            return "register";
        }

        return "registered_successfully";
    }
}
