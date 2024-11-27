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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({MockitoExtension.class})
class ProjectRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    @Autowired
    private ProjectRepository projectRepository;
    @Test
    @DisplayName("Integration test adding new user in repository")
    void addProject() {
        Project project = new Project();
        project.setTitle("New Project");
        project.setStartDate(LocalDate.of(2024, 1, 1));
        project.setEndDate(LocalDate.of(2024, 12, 31));
        project.setDuration(365);

        Project addedProject = projectRepository.addProject(project);

        assertNotNull(addedProject);
        assertNotNull(addedProject.getId());
        assertEquals("New Project", addedProject.getTitle());
        assertEquals(LocalDate.of(2024, 1, 1), addedProject.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 31), addedProject.getEndDate());
        assertEquals(365, addedProject.getDuration());

        logger.info("Test add user: " + addedProject);
    }

    @Test
    void updateProject() {

    }

    @Test
    void deleteProject() {
    }

    @Test
    void getProjectById() {
    }

    @Test
    void getAllProjects() {
    }
}