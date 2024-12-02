package kea.eksamen.service;


import kea.eksamen.dto.SubprojectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import kea.eksamen.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
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

    public List<SubprojectDTO> getSubprojectDtosById(int parentProjectId) {
        List<SubprojectDTO> subprojectDTOS = new ArrayList<>();
        for(Project subproject : projectRepository.getSubProjectsByParentId(parentProjectId)){
            subprojectDTOS.add(mapSubprojectToDto(subproject));
        }
        return subprojectDTOS;
    }

    public SubprojectDTO mapSubprojectToDto(Project subproject){
        SubprojectDTO dto = new SubprojectDTO(subproject.getId(), subproject.getTitle(),
                subproject.getStartDate(), subproject.getEndDate(), subproject.getDuration());
        dto.setHoursToWorkPerDay(getHoursToWorkPerDay(subproject.getId()));
        return dto;
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

    public double getHoursToWorkPerDay(int subprojectId){

        int hoursForAllTasks = taskRepository.getHoursForAllTasks(subprojectId);
        logger.info("Total hours for all tasks for the project id " + subprojectId + " : " + hoursForAllTasks);
        int duration = getProjectById(subprojectId).getDuration();
        if (duration == 0) {
            throw new IllegalArgumentException("Duration cannot be zero.");
        }
        double hoursToWorkPerDay = Math.round(hoursForAllTasks / (double) duration * 10.0) / 10.0;
        logger.info("Hours to  work per day: " + hoursToWorkPerDay);
        return hoursToWorkPerDay;
    }




}
