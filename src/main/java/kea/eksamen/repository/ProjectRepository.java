package kea.eksamen.repository;

import kea.eksamen.model.Project;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Repository
public class ProjectRepository implements ProjectRepositoryInterface {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    private final JdbcClient jdbcClient;

    public ProjectRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Project addProject(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRows = jdbcClient.sql("INSERT INTO PMTool.projects (title, start_date, end_date, duration) " +
                        "VALUES (?, ?, ?, ?)")
                .param(project.getTitle())
                .param(project.getStartDate())
                .param(project.getEndDate())
                .param(project.getDuration())
                .update(keyHolder, "id");
        if (affectedRows != 0 && keyHolder.getKey()!=null) {
            project.setId(keyHolder.getKey().intValue());
            logger.info("Added new project: " + project);
            return project;
        }
        logger.warn("adding new user failed");
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
    public List<Project> getAllProjects() {
        return jdbcClient.sql("SELECT * FROM PMTool.projects")
                .query(Project.class)
                .list();
    }

    @Override
    public Project getProjectById(int id) {
        String sql = "SELECT * FROM PMTool.projects WHERE id = ?";
        return jdbcClient.sql(sql).param(id).query(Project.class).optional().orElse(null);
    }

    public int addSubProject(int parentProjectId, int subProjectId) {
        return jdbcClient.sql("INSERT INTO subprojects (parent_project_id, subproject_id) VALUES (?, ?)")
                .param(parentProjectId)
                .param(subProjectId)
                .update();
    }

    public void removeSubProject(int parentProjectId, int subProjectId) {
        jdbcClient.sql("DELETE FROM subprojects WHERE parent_project_id = ? AND subproject_id = ?")
                .param(parentProjectId)
                .param(subProjectId)
                .update();
    }

    public List<Project> getSubProjectsByParentId(int parentProjectId) {
        return jdbcClient.sql("SELECT * FROM projects p INNER JOIN subprojects s ON p.id = s.subproject_id " +
                        "WHERE s.parent_project_id = ?")
                .param(parentProjectId)
                .query(Project.class)
                .list();
    }
}
