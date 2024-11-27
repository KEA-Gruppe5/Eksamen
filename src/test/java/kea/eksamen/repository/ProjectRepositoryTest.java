package kea.eksamen.repository;

import kea.eksamen.model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({MockitoExtension.class})
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Test
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

    }

    @Test
    void updateProject() {

    }

    @Test
    void deleteProject() {
    }

    @Test
    void findProjectById() {
    }

    @Test
    void findAllProjects() {
    }
}