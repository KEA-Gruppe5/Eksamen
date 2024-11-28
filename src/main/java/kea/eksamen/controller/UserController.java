package kea.eksamen.controller;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.dto.UserDTO;
import kea.eksamen.exceptions.BadCredentialsException;
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

    @GetMapping("/login")
    public String loginPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "user/login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute("user") UserDTO userDTO, HttpSession httpSession,
                               Model model) throws SQLException {
        try {
            User authenticatedUser = userService.authenticate(userDTO);
            if (authenticatedUser != null) {
                int userId = authenticatedUser.getId();
                httpSession.setAttribute("userId", userId);
                if(authenticatedUser.getRole()!=null)httpSession.setAttribute("role", authenticatedUser.getRole().getDisplayName());
                model.addAttribute("userId", userId);
                return "redirect:/projects";
            }
        }catch(BadCredentialsException e){
            model.addAttribute("error", e.getMessage());
        }
        return "user/login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("userId");
        httpSession.invalidate();
        return "redirect:/";
    }

}
