package kea.eksamen.service;


import kea.eksamen.dto.DateRange;
import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Project project : projectRepository.getAllProjects()){
            projectDTOS.add(mapProjectToDto(project));
        }
        return projectDTOS;
    }

    public boolean archiveProject(int id) {
        return projectRepository.archiveProject(id);
    }

    public boolean unarchiveProject(int id) {
        return projectRepository.unarchiveProject(id);
    }

    public List<ProjectDTO> getArchivedProjects() {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Project project : projectRepository.getArchivedProjects()){
            projectDTOS.add(mapProjectToDto(project));
        }
        return projectDTOS;
    }

    public ProjectDTO mapProjectToDto(Project project){
        ProjectDTO dto = new ProjectDTO(project.getId(), project.getTitle(),
                new DateRange(project.getStartDate(),project.getEndDate()), project.getDuration());
        dto.setHoursToWorkPerDay(getHoursToWorkPerDay(project.getId()));
        dto.setHoursForAllTasks(getHoursForAllTasks(project.getId()));
        return dto;
    }

    public double getHoursToWorkPerDay(int projectId){
        double hours = 0.0;
        for(Project subproject : projectRepository.getSubProjectsByParentId(projectId)){
            hours+= subprojectService.getHoursToWorkPerDay(subproject.getId());
        }
        return hours;
    }

    public double getHoursForAllTasks(int projectId){
        double hours = 0.0;
        for(Project subproject : projectRepository.getSubProjectsByParentId(projectId)){
            hours+= subprojectService.getHoursForAllTasks(subproject.getId());
        }
        return hours;
    }

    public boolean isParentProject(int projectId) {
        List<Integer> subProjectIds = new ArrayList<>();
        for (Project project : projectRepository.getAllSubProjects()) {
            Integer id = project.getId();
            subProjectIds.add(id);
        }
        return !subProjectIds.contains(projectId);
    }

}
