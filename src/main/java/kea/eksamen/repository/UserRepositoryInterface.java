package kea.eksamen.repository;

import kea.eksamen.model.User;

public interface UserRepositoryInterface {
    User addUser(User user);
    User findUserByEmail (String email);
}
