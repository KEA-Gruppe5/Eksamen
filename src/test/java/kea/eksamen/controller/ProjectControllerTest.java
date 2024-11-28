package kea.eksamen.controller;
import kea.eksamen.service.ProjectService;
import kea.eksamen.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ProjectController.class)
@ActiveProfiles("test")
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    @DisplayName("ProjectController test: get registration form")
    void addProject_getAddProjectForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(view().name("project/addProject"));
    }
    @Test
    @DisplayName("ProjectController test: list all projects")
    void listProjects_getAllProjects() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projects"))
                .andExpect(view().name("project/projects"));
    }
    @Test
    @DisplayName("ProjectController test: delete a project")
    void deleteProject_postDeleteProject() throws Exception {
        int projectId = 1;

        mockMvc.perform(post("/projects/{id}/delete", projectId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

}