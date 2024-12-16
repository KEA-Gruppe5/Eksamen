package kea.eksamen.repository;

import kea.eksamen.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryInterface {
    User addUser(User user);
    User updateUser(User user, int id);
    boolean deleteUser(int id) throws SQLException;
    User findUserByEmail (String email) throws SQLException;
    List<User> findAllUsers();
}
