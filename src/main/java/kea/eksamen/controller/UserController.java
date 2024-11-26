package kea.eksamen.controller;

import kea.eksamen.model.User;
import kea.eksamen.service.UserService;
import kea.eksamen.util.PasswordValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordValidator passwordValidator;

    public UserController(UserService userService, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.passwordValidator = passwordValidator;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/registerForm";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) throws SQLException {
        if (passwordValidator.isValid(user.getPassword())) {
            userService.saveUser(user);
        }
        return "redirect:/login";
    }

}
