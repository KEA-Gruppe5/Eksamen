package kea.eksamen.service;

import kea.eksamen.model.User;
import kea.eksamen.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTeamService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectTeamService.class);

    public ProjectTeamService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findTeamMembers(int projectId) {
        return userRepository.findTeamMembers(projectId);
    }

    public void assignUserToProject(int projectId, List<Integer> newTeamMembersIds) {
        logger.info("addTeamMember in service is invoked");
        for(Integer i : newTeamMembersIds){
            userRepository.assignUserToProject(i, projectId);
        }
    }

    public List<User> findUnassignedUsers(int projectId){
        return userRepository.findUnassignedUsers(projectId);
    }
}
