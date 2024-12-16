package kea.eksamen.repository;

import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp() {
        taskRepository = new TaskRepository(jdbcClient);
    }


    @Test
    void addTask_createNewTask() {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("This is a test");
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.COMPLETED);
        task.setAssignedUserId(1);
        task.setProjectId(1);

        Task addedTask = taskRepository.addTask(task);
        assertEquals("Test", addedTask.getTitle());
    }


    @Test
    void updateTask_modifyExistingTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setPriority(TaskPriority.MEDIUM);
        updatedTask.setStatus(TaskStatus.PENDING);

        Task result = taskRepository.updateTask(updatedTask, 3); //using taskId 3 to not interfere with taskId 1 which are used for the other tests.

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskPriority.MEDIUM, result.getPriority());
    }

    @Test
    void deleteTask_removeTaskById() {
        boolean deleted = taskRepository.deleteTask(1);

        assertTrue(deleted);

    }

    @Test
    void findTaskById_retrieveTaskDetails() {
        Task task = taskRepository.findTaskById(1);

        assertNotNull(task);
        assertEquals("Task 1 for Beta", task.getTitle());
        assertEquals("Task description for Alpha 1", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals(1, task.getAssignedUserId());
    }

    @Test
    void getAllTasks_retrieveTasksForProject() {
        List<Task> tasks = taskRepository.getAllTasks(2);
        assertFalse(tasks.isEmpty());
        Task task = tasks.get(0);
        assertEquals("Task 1 for Beta", task.getTitle());
        assertEquals("Task description for Alpha 1", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
    }

    @Test
    void assignMember_assignUserToTask() {
        Task task = taskRepository.findTaskById(2);
        assertNotNull(task);
        taskRepository.assignMember(2, 2);
        Task updatedTask = taskRepository.findTaskById(2);
        assertNotNull(updatedTask);
        assertEquals(2, updatedTask.getAssignedUserId());
    }

    @Test
    void findAssignedMember_retrieveAssignedUser() {
        Task task = taskRepository.findTaskById(2);
        taskRepository.assignMember(task.getId(),2);
        assertNotNull(task);

        int foundMember = taskRepository.findAssignedMember(task.getId());
        assertEquals(2,foundMember);
    }

    @Test
    void removeAssignedUser_clearAssignedUser() {
        Task task = taskRepository.findTaskById(2);
        assertNotNull(task);
        taskRepository.removeAssignedUser(2);
        Task updatedTask = taskRepository.findTaskById(2);
        assertNotNull(updatedTask);
        assertEquals(0, updatedTask.getAssignedUserId());
    }


    @Test
    void getHoursForAllTasks_calculateTotalHours(){
        double expected = 18;
        double actual = taskRepository.getHoursForAllTasks(2);
        assertEquals(expected, actual);
    }


}