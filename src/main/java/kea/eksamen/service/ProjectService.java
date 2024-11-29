package kea.eksamen.service;


import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.addProject(project);
    }

    public Project updateProject(Project project, int id) {
        return projectRepository.updateProject(project, id);
    }

    public boolean deleteProject(int id) {
        return projectRepository.deleteProject(id);
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);

    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public void addSubProject(int parentProjectId, int subProjectId) {
        projectRepository.addSubProject(parentProjectId, subProjectId);
    }

    public void removeSubProject(int parentProjectId, int subProjectId) {
        projectRepository.removeSubProject(parentProjectId, subProjectId);
    }

    public List<Project> getSubProjectsByParentId(int parentProjectId) {
        return projectRepository.getSubProjectsByParentId(parentProjectId);
    }


}
