package kea.eksamen.repository;

import kea.eksamen.model.Role;
import kea.eksamen.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({MockitoExtension.class})
class UserRepositoryTest {
    private User user;

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        user = new User("first name", "last name", "email", "password");
        user.setRole(Role.EMPLOYEE);
    }


    @Test
    @DisplayName("Integration test adding new user in repository")
    void addUser()  {
        User savedUser = userRepository.addUser(user);
        assertNotNull(savedUser);
        assertEquals("first name", savedUser.getFirstName());
        assertEquals(1, savedUser.getId()); // 6 because there are inserted 5 users at initialization

        logger.info("Test add user: " + savedUser);
    }

    @Test
    void findUserByEmail() {
        userRepository.addUser(user);
        User foundUser = userRepository.findUserByEmail("email");
        assertNotNull(foundUser);
    }
}