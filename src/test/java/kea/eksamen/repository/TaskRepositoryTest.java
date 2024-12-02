package kea.eksamen.repository;

import kea.eksamen.model.Role;
import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.User;
import kea.eksamen.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;
import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcClient jdbcClient;


    @BeforeEach
    void setUp() {
        taskRepository = new TaskRepository(jdbcClient);
    }


    @AfterEach
    void tearDown() {}

    @Test
    void addTask() {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("This is a test");
        task.setPriority(TaskPriority.HIGH);
        task.setUserId(1);
        task.setProjectId(1);

        Task addedTask = taskRepository.addTask(task, task.getProjectId());
        assertEquals("Test", addedTask.getTitle());
    }


    @Test
    void updateTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setPriority(TaskPriority.MEDIUM);

        Task result = taskRepository.updateTask(updatedTask, 3); //using taskId 3 to not interfere with taskId 1 which are used for the other tests.

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskPriority.MEDIUM, result.getPriority());
    }

    @Test
    void deleteTask() {
        boolean deleted = taskRepository.deleteTask(1);

        assertTrue(deleted);

    }

    @Test
    void findTaskById() {
        Task task = taskRepository.findTaskById(1);

        assertNotNull(task);
        assertEquals("Task 1 for Alpha", task.getTitle());
        assertEquals("Task description for Alpha 1", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals(1, task.getUserId());
    }

    @Test
    void getAllTasks() {
        List<Task> tasks = taskRepository.getAllTasks(1);

        assertFalse(tasks.isEmpty());
        Task task = tasks.get(0);
        assertEquals("Task 1 for Alpha", task.getTitle());
        assertEquals("Task description for Alpha 1", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
    }

    @Test
    void assignMember() {
        Task task = taskRepository.findTaskById(2);
        assertNotNull(task);
        taskRepository.assignMember(2, 2);
        Task updatedTask = taskRepository.findTaskById(2);
        assertNotNull(updatedTask);
        assertEquals(2, updatedTask.getAssignedUserId());
    }

    @Test
    void removeAssignedUser() {
        Task task = taskRepository.findTaskById(2);
        assertNotNull(task);
        taskRepository.removeAssignedUser(2);
        Task updatedTask = taskRepository.findTaskById(2);
        assertNotNull(updatedTask);
        assertEquals(0, updatedTask.getAssignedUserId());
    }


    @Test
    void getHoursForAllTasks(){
        int expected = 148;
        int actual = taskRepository.getHoursForAllTasks(2);
        assertEquals(expected, actual);
    }
}