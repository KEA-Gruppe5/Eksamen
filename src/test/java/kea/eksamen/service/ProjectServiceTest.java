package kea.eksamen.service;

import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SubprojectService subprojectService;

    @InjectMocks
    private ProjectService projectService;

    private Project parentProject;
    private Project subproject1;
    private Project subproject2;

    @BeforeEach
    public void setUp() {
        parentProject = new Project(1, "Parent Project", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 200, false);
        subproject1 = new Project(2, "Subproject 1", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 100, false);
        subproject2 = new Project(3, "Subproject 2", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 31), 150, false);
    }


    @Test
    public void getHoursToWorkPerDay_calculateTotalDailyHours() {
        int projectId = 1;

        List<Project> subprojects = Arrays.asList(subproject1, subproject2);

        when(projectRepository.getSubProjectsByParentId(projectId)).thenReturn(subprojects);
        when(subprojectService.getHoursToWorkPerDay(2)).thenReturn(5.0);
        when(subprojectService.getHoursToWorkPerDay(3)).thenReturn(3.0);

        double result = projectService.getHoursToWorkPerDay(projectId);

        assertEquals(8.0, result); // Expected sum of 5.0 + 3.0 = 8.0 hours
    }

    @Test
    public void getHoursForAllTasks_calculateTotalTaskHours() {
        List<Project> subprojects = Arrays.asList(subproject1, subproject2);
        when(projectRepository.getSubProjectsByParentId(parentProject.getId())).thenReturn(subprojects);

        when(subprojectService.getHoursForAllTasks(2)).thenReturn(10.0);
        when(subprojectService.getHoursForAllTasks(3)).thenReturn(7.0);

        double result = projectService.getHoursForAllTasks(parentProject.getId());

        assertEquals(17.0, result);
    }
}
