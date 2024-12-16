package kea.eksamen.repository;

import kea.eksamen.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ProjectRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepositoryTest.class);

    @Autowired
    private ProjectRepository projectRepository;

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
    void addProject_getAddedProjectWithId() {
        //arrange
        Project project = new Project("winter", LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31), 365);
        //act
        Project addedProject = projectRepository.addProject(project);
        //assert
        assertNotNull(addedProject);
        assertEquals("winter", addedProject.getTitle());
        assertEquals(LocalDate.of(2024, 1, 1), addedProject.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), addedProject.getEndDate());
        assertEquals(365, addedProject.getDuration());
        assertEquals(4, addedProject.getId());
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
        project.setTitle("vinter");
        Project updatedProject = projectRepository.updateProject(project, addedProject.getId());
        //Assert
        assertNotNull(updatedProject);
        assertEquals("vinter", updatedProject.getTitle());
        assertEquals(addedProject.getId(), updatedProject.getId());
    }

    @Test
    @DisplayName("integration test: delete a project")
    void deleteProject_deleteExistingProject() {
        boolean isDeleted = projectRepository.deleteProject(1);
        //Assert
        assertTrue(isDeleted, "The project should be deleted successfully");
        try {
            assertNull(projectRepository.getProjectById(1));
        } catch (IllegalStateException e) {
            assertEquals("No result from ResultSetExtractor", e.getMessage(), "Expected exception due to missing project");
        }
    }

    @Test
    @DisplayName("integration Test: adding a new subproject")
    void addSubProject_addSubProjectToParent() {
        // Arrange
        int parentProjectId = 1; // Ensure this exists in the projects table
        int subProjectId = 3;   // Ensure this exists in the project's table but is not already a subproject of parentProjectId
        assertEquals(1, projectRepository.addSubProject(parentProjectId, subProjectId));
    }

    @Test
    void getSubProjectsByParentId_retrieveSubProjects(){
        List<Project> subprojects = projectRepository.getSubProjectsByParentId(1);
        for(Project project: subprojects){
            System.out.println(project);
        }
        assertEquals(1, subprojects.size());
    }

}