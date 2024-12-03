package kea.eksamen.service;

import kea.eksamen.controller.TaskController;
import kea.eksamen.model.Task;
import kea.eksamen.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);

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
}