package kea.eksamen.repository;
import kea.eksamen.model.Project;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@Repository
public class ProjectRepository implements ProjectRepositoryInterface {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    private JdbcClient jdbcClient;
    public ProjectRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    @Override
    public Project addProject(Project project) {
        int generatedId = jdbcClient.sql("INSERT INTO PMTool.projects (title, start_date, end_date, duration) " +
                        "VALUES (?, ?, ?, ?)")
                .param(project.getTitle())
                .param(project.getStartDate())
                .param(project.getEndDate())
                .param(project.getDuration())
                .update();

        if (generatedId != 0) {
            project.setId(generatedId);
            logger.info("Added new project: " + project);
            return project;
        }
        return null;
    }

    @Override
    public Project updateProject(Project project, int id) {
        int rows = jdbcClient.sql("UPDATE PMTool.projects " +
                        "SET title = ?, start_date = ?, end_date = ?, duration = ? " +
                        "WHERE id = ?")
                .param(project.getTitle())
                .param(project.getStartDate())
                .param(project.getEndDate())
                .param(project.getDuration())
                .param(id)
                .update();

        if (rows > 0) {
            logger.info("Updated project with ID: " + id + " - " + project);
            project.setId(id);
            return project;
        }
        logger.warn("No project found with ID: " + id + ". Update failed.");
        return null;
    }

    @Override
    public Boolean deleteProject(int id) {
        int rows = jdbcClient.sql("DELETE FROM PMTool.projects WHERE id = ?")
                .param(id)
                .update();
        if (rows > 0) {
            logger.info("Successfully deleted project with ID: " + id);
            return true;
        } else {
            logger.warn("No project found with ID: " + id + ". Deletion failed.");
            return false;
        }
    }

    @Override
    public List<Project> findAllProjects() {
        return jdbcClient.sql("SELECT * FROM PMTool.projects")
                .query(resultSet -> {
                    List<Project> projects = new ArrayList<>();
                    while (resultSet.next()) {
                        Project project = new Project();
                        project.setId(resultSet.getInt("id"));
                        project.setTitle(resultSet.getString("title"));
                        project.setStartDate(resultSet.getDate("start_date").toLocalDate());
                        project.setEndDate(resultSet.getDate("end_date").toLocalDate());
                        project.setDuration(resultSet.getInt("duration"));
                        projects.add(project);
                    }
                    return projects;
                });
    }

    @Override
    public Project findProjectById(int id) {
        String sql = "SELECT * FROM PMTool.projects WHERE id = ?";

        return jdbcClient.sql(sql)
                .param(id)
                .query(resultSet -> {
                    if (resultSet.next()) {
                        Project project = new Project();
                        project.setId(resultSet.getInt("id"));
                        project.setTitle(resultSet.getString("title"));
                        project.setStartDate(resultSet.getDate("start_date").toLocalDate());
                        project.setEndDate(resultSet.getDate("end_date").toLocalDate());
                        project.setDuration(resultSet.getInt("duration"));
                        return project;
                    }
                    return null; // No project found
                });
    }

}
