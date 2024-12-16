package kea.eksamen.repository;

import kea.eksamen.model.User;
import kea.eksamen.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
            logger.info("add new user: {}", user);
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
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM PMTool.users u LEFT JOIN PMTool.roles r on u.role_id = r.id WHERE email = ?";
        return jdbcClient.sql(sql)
                .param(email)
                .query(new UserMapper())
                .optional()
                .orElse(null);
    }

    public User findUserById(int id) {
        String sql = "SELECT * FROM PMTool.users u LEFT JOIN PMTool.roles r on u.role_id = r.id WHERE u.id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new UserMapper())
                .optional()
                .orElse(null);
    }

    public List<User> findTeamMembers(int projectId){
        String sql = "SELECT * FROM PMTool.users u LEFT JOIN PMTool.users_projects up on u.id = up.user_id WHERE up.project_id = ?";
        return jdbcClient.sql(sql)
                .param(projectId)
                .query(new UserMapper())
                .list();
    }


    public void assignUserToProject(int userId, int projectId) {
        String sql = "INSERT INTO PMTool.users_projects(user_id, project_id) VALUES (?, ?)";
        int affectedRows = jdbcClient.sql(sql)
                .param(userId)
                .param(projectId)
                .update();
        if(affectedRows>0){
            logger.info("adding user with id {}  to the project with id {}", userId, projectId);
        }
    }

    public void removeUserFromProject(int userId, int projectId) {
        String sql = "DELETE FROM PMTool.users_projects up WHERE up.user_id = ? AND up.project_id = ?";
        int affectedRows = jdbcClient.sql(sql)
                .param(userId)
                .param(projectId)
                .update();
        if(affectedRows>0){
            logger.info("delete user with id {} from the project with id {}", userId, projectId);
        }
    }

    public List<User> findUnassignedUsers(int projectId) {
        String sql = "SELECT id, firstname, lastname, email, password, role_id, GROUP_CONCAT(up.project_id) " + //concat to remove duplicates among users with multiple projects
                "FROM PMTool.users u LEFT JOIN PMTool.users_projects up ON u.id = up.user_id " +
                "WHERE u.id NOT IN (SELECT user_id FROM PMTool.users_projects up2 WHERE up2.project_id = ?) " + //exclude users assigned in this project
                "GROUP BY u.id";
        return jdbcClient.sql(sql)
                .param(projectId)
                .query(User.class)
                .list();
    }
}
