package kea.eksamen.repository;

import kea.eksamen.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({MockitoExtension.class})
@Transactional
class ProjectRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Integration test: get project via ID")
    void getProjectById_retrieveCorrectProject() {
        //act
        Project retrievedProject = projectRepository.getProjectById(1);
        //assert
        assertNotNull(retrievedProject);
        assertEquals(1, retrievedProject.getId());
        assertEquals("Project Alpha", retrievedProject.getTitle());
        assertEquals(LocalDate.of(2024, 1, 1), retrievedProject.getStartDate());
        assertEquals(LocalDate.of(2024, 3, 1), retrievedProject.getEndDate());
        assertEquals(60, retrievedProject.getDuration());
    }

    @Test
    @DisplayName("Integration test: get all projects")
    void getAllProjects_retrieveAllProjects() {
        // Act
        List<Project> projects = projectRepository.getAllProjects();
        // Assert
        assertNotNull(projects, "The projects list should not be null");
        assertEquals(3, projects.size(), "There should be 3 predefined projects in the test database");
    }

    @Test
    @DisplayName("Integration test: adding new project")
    void addProject_addNewProject() {
        //arrange
        Project project = new Project();
        project.setTitle("winter");
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setDuration(365);
        //act
        Project addedProject = projectRepository.addProject(project);
        //assert
        assertNotNull(addedProject);
        assertNotNull(addedProject.getId());
        assertEquals("winter", addedProject.getTitle());
        assertEquals(LocalDate.of(2024, 1, 1), addedProject.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), addedProject.getEndDate());
        assertEquals(365, addedProject.getDuration());
        logger.info("Test addProject: " + addedProject);
    }

    @DisplayName("integration test: update a project")
    @Test
    void updateProject_updateExistingProject() {
        //arrange
        Project project = new Project();
        project.setTitle("summer");
        project.setStartDate(LocalDate.of(2024, 2, 1));
        project.setEndDate(LocalDate.of(2024, 6, 1));
        project.setDuration(120);
        Project addedProject = projectRepository.addProject(project);
        //act
        project.setDuration(100);
        Project updatedProject = projectRepository.updateProject(project, addedProject.getId());
        //Assert
        assertNotNull(updatedProject);
        assertEquals(100, updatedProject.getDuration());
        assertEquals(addedProject.getId(), updatedProject.getId());
    }

    @Test
    @DisplayName("integration test: delete a project")
    void deleteProject_deleteExistingProject() {
        // Arrange
        Project project = new Project();
        project.setTitle("Project to Delete");
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setDuration(365);
        Project addedProject = projectRepository.addProject(project);
        assertNotNull(addedProject, "The project should be added before deletion");
        int projectId = addedProject.getId();
        // Act
        boolean isDeleted = projectRepository.deleteProject(projectId);
        //Assert
        assertTrue(isDeleted, "The project should be deleted successfully");
        try {
            assertNull(projectRepository.getProjectById(projectId));
        } catch (IllegalStateException e) {
            assertEquals("No result from ResultSetExtractor", e.getMessage(), "Expected exception due to missing project");
        }
    }

    @Test
    @DisplayName("integration Test:  adding a new subproject")
    void AddSubProject_addASubproject() {
        // Arrange
        int parentProjectId = 1; // Ensure this exists in the projects table
        int subProjectId = 3;   // Ensure this exists in the projects table but is not already a subproject of parentProjectId
        //Act
        projectRepository.addSubProject(parentProjectId, subProjectId);
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM subprojects WHERE parent_project_id = ? AND subproject_id = ?",
                Integer.class,
                parentProjectId,
                subProjectId
        );
        //assert
        assertNotNull(count);
        assertEquals(1, count, "The subproject relationship should exist in the database");
    }

}