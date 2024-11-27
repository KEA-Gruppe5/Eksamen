package kea.eksamen.repository;

import kea.eksamen.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements UserRepositoryInterface{
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRows = jdbcClient.sql("INSERT INTO PMTool.users (firstname, lastname, email," +
                        "password, role_id) VALUES (?, ?, ?, ?, ?)")
                .param(user.getFirstName())
                .param(user.getLastName())
                .param(user.getEmail())
                .param(user.getPassword())
                .param(user.getRole().getId())
                .update(keyHolder, "id");

        if(affectedRows!=0 && keyHolder.getKey()!=null){
            user.setId(keyHolder.getKey().intValue());
            logger.info("add new user: " + user);
            return user;
        }
        logger.warn("adding new user failed");
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
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM PMTool.users WHERE email = ?";
        return jdbcClient.sql(sql)
                .param(email)
                .query(User.class)
                .optional()
                .orElse(null);
    }

    public List<User> findTeamMembers(int projectId){
        String sql = "SELECT * FROM PMTool.users u LEFT JOIN PMTool.users_projects up on u.id = up.user_id WHERE up.project_id = ?";
        return jdbcClient.sql(sql)
                .param(projectId)
                .query(User.class)
                .list();
    }
}
