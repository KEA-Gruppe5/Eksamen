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
    private final SubprojectService subprojectService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    public ProjectService(ProjectRepository projectRepository, SubprojectService subprojectService) {
        this.projectRepository = projectRepository;
        this.subprojectService = subprojectService;
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

    public boolean archiveProject(int id) {
        return projectRepository.archiveProject(id);
    }

    public boolean unarchiveProject(int id) {
        return projectRepository.unarchiveProject(id);
    }

    public List<Project> getArchivedProjects() {
        return projectRepository.getArchivedProjects();
    }


    public double getHoursToWorkPerDay(int projectId){
        return 0.0;
    }

    public boolean isParentProject(int projectId) {
        List<Integer> subProjectIds = projectRepository.getAllSubProjects().stream()
                .map(Project::getId)
                .toList();
        return !subProjectIds.contains(projectId);
    }

}
