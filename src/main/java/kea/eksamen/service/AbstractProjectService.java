package kea.eksamen.service;

import kea.eksamen.dto.DateRange;
import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;

public abstract class AbstractProjectService {

    private final ProjectRepository projectRepository;

    protected AbstractProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public ProjectDTO getProjectById(int id) {
        return mapProjectToDto(projectRepository.getProjectById(id));
    }

    public Project addProject(ProjectDTO project) {
        return projectRepository.addProject(mapDtoToProject(project));
    }

    public void updateProject(ProjectDTO project, int id) {
        projectRepository.updateProject(mapDtoToProject(project), id);
    }

    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }

    public ProjectDTO mapProjectToDto(Project project){
        ProjectDTO dto = new ProjectDTO(project.getId(), project.getTitle(),
                new DateRange(project.getStartDate(),project.getEndDate()), project.getDuration());
        dto.setHoursToWorkPerDay(getHoursToWorkPerDay(project.getId()));
        dto.setHoursForAllTasks(getHoursForAllTasks(project.getId()));
        return dto;
    }

    public Project mapDtoToProject(ProjectDTO projectDto){
        Project project = new Project(projectDto.getTitle(),
                projectDto.getDateRange().getStartDate(), projectDto.getDateRange().getEndDate(),
                projectDto.getDuration());
        return project;
    }

    public abstract double getHoursToWorkPerDay(int projectId);
    public abstract double getHoursForAllTasks(int projectId);

}
