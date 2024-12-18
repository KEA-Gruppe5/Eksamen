package kea.eksamen.controller;

import kea.eksamen.dto.UserDTO;
import kea.eksamen.model.Role;
import kea.eksamen.model.User;
import kea.eksamen.service.UserService;
import kea.eksamen.util.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordValidator passwordValidator;

    @Test
    @DisplayName("UserController test: get registration form")
    void getRegistrationForm_displayRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/registerForm"));
    }

    @Test
    @DisplayName("UserController test: registration successful")
    void saveUser_validRegistration() throws Exception {
        // Arrange
        User user = new User("FirstName", "LastName", "email@test", "kea123");
        user.setRole(Role.EMPLOYEE);
        // Mock password validation
        when(passwordValidator.isValid(user.getPassword())).thenReturn(true);

        // Mock saveUser to return the user
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/register")
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("user/registerForm")); // Assert redirection to /login

        // Verify that saveUser was called
        verify(userService, times(1)).saveUser(any(User.class));
    }
    @Test
    @DisplayName("UserController test: get login form")
    void loginPage_displayLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/login"));
    }
    @Test
    @DisplayName("UserController test: login successful")
    void Authentication_successfulLogin() throws Exception {
        UserDTO userDTO = new UserDTO("email@test", "kea123");
        User user = new User("FirstName", "LastName", "email@test", "kea123");
        user.setId(1);
        user.setRole(Role.EMPLOYEE);
        when(userService.authenticate(userDTO)).thenReturn(user);

        mockMvc.perform(post("/login")
                        .flashAttr("user", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"))
                .andExpect(request().sessionAttribute("userId", user.getId()))
                .andExpect(request().sessionAttribute("role", user.getRole().getDisplayName())); // redirection to /login

        // Verify that saveUser was called
        verify(userService, times(1)).authenticate(any(UserDTO.class));
    }
    @Test
    @DisplayName("UserController test: logout successful")
    void logout_clearSession() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(request().sessionAttributeDoesNotExist("userId"))
                .andExpect(status().is3xxRedirection());
    }
}