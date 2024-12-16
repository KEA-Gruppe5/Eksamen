package kea.eksamen.repository;

import kea.eksamen.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryInterface {
    User addUser(User user);
    User findUserByEmail (String email);
}
