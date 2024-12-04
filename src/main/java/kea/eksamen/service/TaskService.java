package kea.eksamen.service;


import kea.eksamen.dto.TaskDTO;
import kea.eksamen.dto.TeamMemberDTO;
import kea.eksamen.model.Project;
import kea.eksamen.model.Task;
import kea.eksamen.model.User;
import kea.eksamen.repository.TaskRepository;
import kea.eksamen.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectTeamService projectTeamService;


    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectTeamService projectTeamService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectTeamService = projectTeamService;
    }

    public Task addTask(Task task) {
        Task addedTask = taskRepository.addTask(task);
        return addedTask;
    }

    public List<Task> getAllTasks(int projectId) {

        return taskRepository.getAllTasks(projectId);
    }

    public boolean deleteTask(int taskId) {
        return taskRepository.deleteTask(taskId);
    }

    public Task editTask(Task task, int taskId) {
        return taskRepository.updateTask(task, taskId);
    }

    public Task findTaskById(int taskId) {
        return taskRepository.findTaskById(taskId);
    }

    public List<TeamMemberDTO> getMembersFromTeam(int projectId){
        return projectTeamService.findTeamMembers(projectId);
    }

    public void assignMemberToTask(int taskId, int userId) {
        taskRepository.assignMember(taskId, userId);
    }

    public int getAssignedMember(int taskId){
        return taskRepository.findAssignedMember(taskId);
    }

    public void removeAssignedUser(int taskId) {
        taskRepository.removeAssignedUser(taskId);
    }

    public List<TaskDTO> getTaskDtosByProjectId(int projectId) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskRepository.getAllTasksByProjectId(projectId)) {
            taskDTOList.add(mapTaskToDto(task));
        }
        return taskDTOList;
    }

    public TaskDTO mapTaskToDto(Task task) {
        TeamMemberDTO teamMemberDTO = null;

        if (task.getAssignedUserId() != 0) {
            User user = userRepository.findUserById(task.getAssignedUserId());
            if (user != null) {
                teamMemberDTO = new TeamMemberDTO(user.getFirstName() + " " + user.getLastName(), user.getEmail());
            }
        }
        return new TaskDTO(
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getDisplayName(),
                teamMemberDTO,
                task.getEstimatedHours()
        );
    }
}
