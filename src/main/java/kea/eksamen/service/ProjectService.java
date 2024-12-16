package kea.eksamen.service;


import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService extends AbstractProjectService{
    private final ProjectRepository projectRepository;
    private final SubprojectService subprojectService;

    public ProjectService(ProjectRepository projectRepository, SubprojectService subprojectService) {
        super(projectRepository);
        this.projectRepository = projectRepository;
        this.subprojectService = subprojectService;
    }

    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Project project : projectRepository.getAllProjects()){
            projectDTOS.add(mapProjectToDto(project));
        }
        return projectDTOS;
    }

    public void archiveProject(int id) {
        projectRepository.archiveProject(id);
    }

    public void unarchiveProject(int id) {
        projectRepository.unarchiveProject(id);
    }

    public List<ProjectDTO> getArchivedProjects() {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Project project : projectRepository.getArchivedProjects()){
            projectDTOS.add(mapProjectToDto(project));
        }
        return projectDTOS;
    }

    public boolean isParentProject(int projectId) {
        List<Integer> subProjectIds = new ArrayList<>();
        for (Project project : projectRepository.getAllSubProjects()) {
            Integer id = project.getId();
            subProjectIds.add(id);
        }
        return !subProjectIds.contains(projectId);
    }

    @Override
    public double getHoursToWorkPerDay(int projectId){
        double hours = 0.0;
        for(Project subproject : projectRepository.getSubProjectsByParentId(projectId)){
            hours+= subprojectService.getHoursToWorkPerDay(subproject.getId());
        }
        return hours;
    }

    @Override
    public double getHoursForAllTasks(int projectId){
        double hours = 0.0;
        for(Project subproject : projectRepository.getSubProjectsByParentId(projectId)){
            hours+= subprojectService.getHoursForAllTasks(subproject.getId());
        }
        return hours;
    }
}
