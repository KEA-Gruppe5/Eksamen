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
import java.util.List;

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
        assertEquals(4, savedUser.getId()); // 4 because there are inserted 3 users at initialization

        logger.info("Test add user: " + savedUser);
    }

    @Test
    @DisplayName("Integration test finding user by email in repository")
    void findUserByEmail() {
        User user =  new User("first name", "last name", "some email", "password");
        user.setRole(Role.EMPLOYEE);
        userRepository.addUser(user);
        User foundUser = userRepository.findUserByEmail("some email");
        assertNotNull(foundUser);
        assertEquals("first name", foundUser.getFirstName());
    }

    @Test
    @DisplayName("Integration test finding users assigned to a project")
    void testFindTeamMembersByProjectId() {
        List<User> team = userRepository.findTeamMembers(1);
        assertEquals(2, team.size());
        for(User user : team){
            System.out.println(user);
        }
    }
}