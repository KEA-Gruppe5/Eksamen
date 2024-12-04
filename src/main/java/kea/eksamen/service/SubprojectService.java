package kea.eksamen.service;


import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import kea.eksamen.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubprojectService extends AbstractProjectService{

    private static final Logger logger = LoggerFactory.getLogger(SubprojectService.class);

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public SubprojectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        super(projectRepository);
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Project addSubProject(ProjectDTO subProject, int parentId) {
        Project addedSubproject = super.addProject(subProject);
        if (addedSubproject != null) {
            projectRepository.addSubProject(parentId, addedSubproject.getId());
        }
        return addedSubproject;
    }

    public int getParentId(int subProjectId){
        return projectRepository.getParentIdBySubProjectId(subProjectId);
    }

    public List<ProjectDTO> getSubprojectsByParentId(int parentProjectId) {
        List<ProjectDTO> subprojectDTOS = new ArrayList<>();
        for(Project subproject : projectRepository.getSubProjectsByParentId(parentProjectId)){
            subprojectDTOS.add(mapProjectToDto(subproject));
        }
        return subprojectDTOS;
    }

    @Override
    public double getHoursToWorkPerDay(int subprojectId){

        double hoursForAllTasks = getHoursForAllTasks(subprojectId);
        int duration = projectRepository.getProjectById(subprojectId).getDuration();
        if (duration == 0) {
            throw new IllegalArgumentException("Duration cannot be zero.");
        }
        double hoursToWorkPerDay = Math.round(hoursForAllTasks / (double) duration * 10.0) / 10.0;
        logger.info("Hours to  work per day: " + hoursToWorkPerDay);
        return hoursToWorkPerDay;
    }

    @Override
    public double getHoursForAllTasks(int subprojectId){
        double hoursForAllTasks = taskRepository.getHoursForAllTasks(subprojectId);
        logger.info("Total hours for all tasks for the subproject " + subprojectId + " : " + hoursForAllTasks);
        return hoursForAllTasks;
    }

}
