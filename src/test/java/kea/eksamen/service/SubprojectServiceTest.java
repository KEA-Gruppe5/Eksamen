package kea.eksamen.service;

import kea.eksamen.model.Project;
import kea.eksamen.repository.ProjectRepository;
import kea.eksamen.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SubprojectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private SubprojectService subprojectService;

    private Project subproject;

    @BeforeEach
    public void setUp() {
        assertNotNull(logger);
        subproject = new Project(1, "Parent Project", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 200, false);
    }


    @Test
    public void testGetHoursForAllTasks() {
        int subprojectId = subproject.getId();
        double expectedHoursForAllTasks = 50.0;

        when(taskRepository.getHoursForAllTasks(subprojectId)).thenReturn(expectedHoursForAllTasks);

        double result = subprojectService.getHoursForAllTasks(subprojectId);
        assertEquals(expectedHoursForAllTasks, result);
    }

    @Test
    public void testGetHoursToWorkPerDay() {
        int subprojectId = subproject.getId();
        double hoursForAllTasks = 1000.0;
        int duration = subproject.getDuration();

        when(taskRepository.getHoursForAllTasks(subprojectId)).thenReturn(hoursForAllTasks);
        when(projectRepository.getProjectById(subprojectId)).thenReturn(subproject);

        double result = subprojectService.getHoursToWorkPerDay(subprojectId);
        double expectedHoursPerDay = hoursForAllTasks / duration;
        assertEquals(Math.round(expectedHoursPerDay), Math.round(result));
    }
}