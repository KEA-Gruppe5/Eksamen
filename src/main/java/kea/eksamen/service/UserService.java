package kea.eksamen.service;

import kea.eksamen.dto.UserDTO;
import kea.eksamen.exceptions.BadCredentialsException;
import kea.eksamen.exceptions.EmailAlreadyExistsException;
import kea.eksamen.model.User;
import kea.eksamen.repository.UserRepository;
import kea.eksamen.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        logger.info("saveUser is called.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkIfUserAlreadyExists(user.getEmail());
        User savedUser = userRepository.addUser(user);
        logger.info("Password:" + savedUser.getPassword());
        return savedUser;
    }

    public void checkIfUserAlreadyExists(String email) {
        logger.info("checkIfUserAlreadyExists is called.");
        User foundByEmailUser = userRepository.findUserByEmail(email);
        if(foundByEmailUser != null){
            throw new EmailAlreadyExistsException();
        }
    }

    public User authenticate(UserDTO userDTO) {
        User user = userRepository.findUserByEmail(userDTO.getEmail());
        if(user != null){
                boolean isPasswordCorrect = passwordEncoder.matches(userDTO.getPassword(),
                        user.getPassword());
                if(isPasswordCorrect){  //in case password is encrypted
                    logger.info("User authenticated: " + user);
                    return user;
                }
                if(user.getPassword().equals(userDTO.getPassword())){ //if it is not encrypted
                    logger.info("User authenticated: " + user);
                    return user;
                }
                logger.info("User is not authenticated.\nPassword:"
                        + userDTO.getPassword()+"\nPassword in db: " + user.getPassword());

        }
        throw new BadCredentialsException();
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
