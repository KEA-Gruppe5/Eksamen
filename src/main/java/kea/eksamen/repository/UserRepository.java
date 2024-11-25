package kea.eksamen.repository;

import kea.eksamen.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserRepository implements UserRepositoryInterface{
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User addUser(User user) {
        int generatedId = jdbcClient.sql("INSERT INTO PMTool.users (firstname, lastname, email," +
                        "password, role_id) VALUES (?, ?, ?, ?, ?)")
                .param(user.getFirstName())
                .param(user.getLastName())
                .param(user.getEmail())
                .param(user.getPassword())
                .param(user.getRole().getId())
                .update();

        if(generatedId!=0){
            user.setId(generatedId);
            logger.info("add new user: " + user);
            return user;
        }
        return null;
    }

    @Override
    public User updateUser(User user, int id) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return false;
    }

    @Override
    public User findUserByEmail(String email) throws SQLException {
        return null;
    }
}
