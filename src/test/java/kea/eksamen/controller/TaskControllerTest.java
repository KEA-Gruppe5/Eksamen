package kea.eksamen.controller;

import kea.eksamen.dto.TaskDTO;
import kea.eksamen.dto.TeamMemberDTO;
import kea.eksamen.model.*;
import kea.eksamen.service.SubprojectService;
import kea.eksamen.service.TaskService;
import kea.eksamen.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;
    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private SubprojectService subprojectService;

    private TaskDTO taskDTO;
    private User user;
    private List<User> userList;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(1, 1, "Test Task", "description", TaskPriority.MEDIUM, TaskStatus.TODO, LocalDate.now(), 0, 8);
        userList = new ArrayList<>();
        user = new User("FirstName", "LastName", "email@test", "kea123");
        user.setRole(Role.EMPLOYEE);
        userList.add(user);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userId", 1);
        taskDTO = new TaskDTO(1, 1, "Test Task", "description", "MEDIUM", null,LocalDate.now() ,new TeamMemberDTO("test","test"),2);
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

        when(taskService.addTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/project/{projectId}/add", task.getProjectId())
                        .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + task.getProjectId() + "/tasks"));

        verify(taskService, times(1)).addTask(any(Task.class));
    }


    @Test
    void viewTasks() throws Exception {

        when(taskService.getTaskDtosByProjectId(task.getProjectId())).thenReturn(List.of(taskDTO));
        mockMvc.perform(get("/project/{projectId}/tasks", task.getProjectId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("task/tasks"))
                .andExpect(model().attribute("tasks", List.of(taskDTO)))
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
        List<TeamMemberDTO> members = Arrays.asList(
                new TeamMemberDTO("member1", "Member One"),
                new TeamMemberDTO("member2", "Member Two")
        );
        when(taskService.getMembersFromTeam(subprojectService.getParentId(task.getId()))).thenReturn(members);

        mockMvc.perform(get("/project/{projectId}/{taskId}/assign", task.getProjectId(), task.getId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("task/assignTask"))
                .andExpect(model().attribute("taskId", task.getId()))
                .andExpect(model().attribute("projectId", task.getProjectId()))
                .andExpect(model().attribute("assignedMember", subprojectService.getParentId(task.getId())))
                .andExpect(model().attribute("users", taskService.getMembersFromTeam(subprojectService.getParentId(task.getId()))));
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