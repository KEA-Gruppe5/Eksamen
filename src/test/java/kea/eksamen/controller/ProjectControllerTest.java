package kea.eksamen.controller;

import jakarta.validation.ConstraintValidatorContext;
import kea.eksamen.dto.DateRange;
import kea.eksamen.service.ProjectService;
import kea.eksamen.service.SubprojectService;
import kea.eksamen.util.DateRangeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;
    @MockBean
    private ProjectService projectService;

    @MockBean
    private SubprojectService subprojectService;

    @BeforeEach
    void setUp() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userId", 1);
    }

    @Test
    @DisplayName("ProjectController test: get registration form")
    void addProject_getRegistrationForm() throws Exception {
        mockMvc.perform(get("/projects/add").session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(view().name("project/addProject"));
    }
    @Test
    @DisplayName("ProjectController test: list all projects")
    void listProjects_getAllProjects() throws Exception {
        mockMvc.perform(get("/projects").session(mockHttpSession))
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

    @Test
    @DisplayName("DateRangeValidator test: invalid date range")
    public void isValid_invalidDateRange() {
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(LocalDate.of(2023, 12, 1));
        dateRange.setEndDate(LocalDate.of(2023, 12, 1));

        DateRangeValidator validator = new DateRangeValidator();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        assertFalse(validator.isValid(dateRange,context));
    }
}