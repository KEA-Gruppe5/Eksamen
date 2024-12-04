package kea.eksamen.service;

import kea.eksamen.model.Task;
import kea.eksamen.repository.TaskRepository;
import kea.eksamen.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    private TaskService taskService;
    private ProjectTeamService projectTeamService;

    private Task task;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        projectTeamService = mock(ProjectTeamService.class);

        taskService = new TaskService(taskRepository, userRepository, projectTeamService);

        task = new Task();
        task.setId(1);
        task.setTitle("Test Task");
        task.setAssignedUserId(1);
        task.setProjectId(1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addTask() {
        when(taskRepository.addTask(task)).thenReturn(task);
        Task result = taskService.addTask(task);
        assertEquals(task, result);
        verify(taskRepository, times(1)).addTask(task);
    }

    @Test
    void getAllTasks() {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.getAllTasks(task.getProjectId())).thenReturn(tasks);
        List<Task> result = taskService.getAllTasks(task.getProjectId());
        assertEquals(tasks, result);
        verify(taskRepository, times(1)).getAllTasks(task.getProjectId());
    }

    @Test
    void deleteTask() {
        when(taskRepository.deleteTask(task.getId())).thenReturn(true);
        boolean result = taskService.deleteTask(task.getId());
        assertTrue(result);
        verify(taskRepository, times(1)).deleteTask(task.getId());
    }

    @Test
    void editTask() {
        when(taskRepository.updateTask(task, task.getId())).thenReturn(task);
        Task result = taskService.editTask(task, task.getId());
        assertEquals(task, result);
        verify(taskRepository, times(1)).updateTask(task, task.getId());
    }

    @Test
    void findTaskById() {
        when(taskRepository.findTaskById(task.getId())).thenReturn(task);
        Task result = taskService.findTaskById(task.getId());
        assertEquals(task, result);
        verify(taskRepository, times(1)).findTaskById(task.getId());
    }

    @Test
    void assignMemberToTask() {
        int userId = 1;
        taskService.assignMemberToTask(task.getId(), userId);
        verify(taskRepository).assignMember(task.getId(), userId);
    }

    @Test
    void removeAssignedUser() {
        taskService.removeAssignedUser(task.getId());
        verify(taskRepository).removeAssignedUser(task.getId());
    }

    @Test
    void getMembersFromTeam() {
        taskService.getMembersFromTeam(task.getProjectId());
        verify(projectTeamService).findTeamMembers(task.getProjectId());
    }

    @Test
    void getAssignedMember() {
        taskService.getAssignedMember(task.getId());
        verify(taskRepository).findAssignedMember(task.getId());
    }
}