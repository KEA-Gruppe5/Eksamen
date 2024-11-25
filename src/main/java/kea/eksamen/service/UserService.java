package kea.eksamen.service;

import kea.eksamen.exceptions.EmailAlreadyExistsException;
import kea.eksamen.model.User;
import kea.eksamen.repository.UserRepository;
import kea.eksamen.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) throws SQLException {
        logger.info("saveUser is called.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkIfUserAlreadyExists(user.getEmail());
        User savedUser = userRepository.addUser(user);
        logger.info("Password:" + savedUser.getPassword());
        return savedUser;
    }

    public void checkIfUserAlreadyExists(String email) throws SQLException {
        logger.info("checkIfUserAlreadyExists is called.");
        User foundByEmailUser = userRepository.findUserByEmail(email);
        if(foundByEmailUser != null){
            throw new EmailAlreadyExistsException();
        }
    }
}
