package kea.eksamen.service;

import kea.eksamen.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void testGetArchivedProjects() {
        List<Project> archivedProjects = projectService.getArchivedProjects();
        System.out.println("Archived Projects: " + archivedProjects);
    }
}
