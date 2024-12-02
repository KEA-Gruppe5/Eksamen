package kea.eksamen.controller;

import kea.eksamen.model.Role;
import kea.eksamen.model.Task;
import kea.eksamen.model.User;
import kea.eksamen.service.TaskService;
import kea.eksamen.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;
    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    private User users;
    private List<User> userList;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1);
        task.setTitle("Test Task");
        task.setUserId(1);
        task.setProjectId(1);
        userList = new ArrayList<>();
        users = (new User("FirstName", "LastName", "email@test", "kea123"));
        users.setRole(Role.EMPLOYEE);
        userList.add(users);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userId", 1);


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void addTask() throws Exception {
        mockMvc.perform(get("/project/{projectId}/add", task.getProjectId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("addNewTask"))
                .andExpect(model().attribute("projectId", task.getProjectId()))
                .andExpect(view().name("task/addTask"));
    }

    @Test
    void testAddTask() throws Exception {

        when(taskService.addTask(any(Task.class), eq(task.getProjectId()))).thenReturn(task);

        mockMvc.perform(post("/project/{projectId}/add", task.getProjectId())
                        .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).addTask(any(Task.class), eq(task.getProjectId()));
    }


    @Test
    void viewTasks() throws Exception {

        when(taskService.getAllTasks(task.getProjectId())).thenReturn(List.of(task));

        mockMvc.perform(get("/project/{projectId}/tasks", task.getProjectId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("task/tasks"))
                .andExpect(model().attribute("tasks", List.of(task)))
                .andExpect(model().attribute("projectId", task.getProjectId()));
    }

    @Test
    void deleteTask() throws Exception {


        mockMvc.perform(post("/project/{projectId}/{taskId}/delete", task.getProjectId(), task.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).deleteTask(task.getId());
    }

    @Test
    void editTask() throws Exception {

        when(taskService.findTaskById(task.getId())).thenReturn(task);

        mockMvc.perform(get("/project/{projectId}/{taskId}/edit", task.getProjectId(), task.getId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("task/editTask"))
                .andExpect(model().attribute("editTask", task));
    }

    @Test
    void testEditTask() throws Exception {
        when(taskService.editTask(any(Task.class), eq(task.getId()))).thenReturn(task);

        mockMvc.perform(post("/project/{projectId}/{taskId}/edit", task.getProjectId(), task.getId())
                        .param("title", "Updated Task Title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).editTask(any(Task.class), eq(task.getId()));
    }

    @Test
    void assignMember() throws Exception {
        when(userService.findAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/project/{projectId}/{taskId}/assign", task.getProjectId(), task.getId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("task/assignTask"))
                .andExpect(model().attribute("taskId", task.getId()))
                .andExpect(model().attribute("projectId", task.getProjectId()))
                .andExpect(model().attribute("users", userList));
    }

    @Test
    void assignTeamMemberToTask() throws Exception {
        int userIdToAssign = 1;
        doNothing().when(taskService).assignMemberToTask(task.getId(), userIdToAssign);
        mockMvc.perform(post("/project/{projectId}/{taskId}/assign", task.getProjectId(), task.getId())
                        .param("userIdToAssign", String.valueOf(userIdToAssign)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).assignMemberToTask(task.getId(), userIdToAssign);
    }

    @Test
    void removeAssignedUser() throws Exception {
        int userIdToAssign = 1;
        doNothing().when(taskService).removeAssignedUser(task.getId());
        mockMvc.perform(post("/project/{projectId}/{taskId}/removeMember", task.getProjectId(), task.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).removeAssignedUser(task.getId());
    }
}