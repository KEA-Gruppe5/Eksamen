package kea.eksamen.service;

import kea.eksamen.model.User;
import kea.eksamen.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTeamService {

    private final UserRepository userRepository;

    public ProjectTeamService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getTeamMembers(int projectId) {
        return userRepository.findTeamMembers(projectId);
    }
}
