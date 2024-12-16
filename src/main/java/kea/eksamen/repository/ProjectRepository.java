package kea.eksamen.repository;
import kea.eksamen.model.Project;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
        int affectedRows = jdbcClient.sql("INSERT INTO PMTool.projects (title, start_date, end_date, duration, archived) " +
                        "VALUES (?, ?, ?, ?, ?)")
                .param(project.getTitle())
                .param(project.getStartDate())
                .param(project.getEndDate())
                .param(project.getDuration())
                .param(false)
                .update(keyHolder, "id");
        if (affectedRows != 0 && keyHolder.getKey() != null) {
            project.setId(keyHolder.getKey().intValue());
            logger.info("Added new project: " + project);
            return project;
        }
        logger.warn("Adding new project failed");
        return null;
    }

    @Override
    public Project updateProject(Project project, int id) {
        int rows = jdbcClient.sql("UPDATE PMTool.projects " +
                        "SET title = ?, start_date = ?, end_date = ?, duration = ?, archived = ? " +
                        "WHERE id = ?")
                .param(project.getTitle())
                .param(project.getStartDate())
                .param(project.getEndDate())
                .param(project.getDuration())
                .param(false)
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
        //get all the subProject ids from the parent project id.
        List<Integer> subProjectsId = jdbcClient.sql("SELECT subproject_id FROM PMTool.subprojects WHERE parent_project_id = ?")
                .param(id) //binds Parent id to ?
                .query(Integer.class) //maps the result (all the subProject id values) to a list of(Integers), since java can't read Database rows.
                .list(); // execute database returns list(Integer), that's stored in the variable subProjectsId
        for (Integer subProjects : subProjectsId) {
            //looping and get all subprojects and delete them
            jdbcClient.sql("DELETE FROM PMTool.projects WHERE id = ?")
                    .param(subProjects)
                    .update();
            logger.info("Deleted subproject with ID: " + subProjects);
        }
        // delete the parent project
        int rows = jdbcClient.sql("DELETE FROM PMTool.projects WHERE id = ?")
                .param(id)
                .update();
        if (rows > 0) {
            logger.info("projects delete" + id);
            return true;
        } else {

            logger.warn("No project found with ID: " + id + ". Deletion failed.");
            return false;
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return jdbcClient.sql("SELECT * FROM PMTool.projects WHERE archived = false")
                .query(Project.class)
                .list();
    }

    public List<Project> getArchivedProjects() {
        return jdbcClient.sql("SELECT * FROM PMTool.projects WHERE archived = true")
                .query(Project.class)
                .list();
    }

    public void archiveProject(int id) {
        int rows = jdbcClient.sql("UPDATE PMTool.projects SET archived = true WHERE id = ?")
                .param(id)
                .update();
        if (rows > 0) {
            logger.info("Archived project with ID: " + id);
        }
        logger.warn("Archiving project failed for ID: " + id);
    }

    public void unarchiveProject(int id) {
        int rows = jdbcClient.sql("UPDATE PMTool.projects SET archived = false WHERE id = ?")
                .param(id)
                .update();
        if (rows > 0) {
            logger.info("Unarchived project with ID: " + id);
        }
        logger.warn("Unarchiving project failed for ID: " + id);
    }

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


    public List<Project> getSubProjectsByParentId(int parentProjectId) {
        return jdbcClient.sql("SELECT * FROM projects p INNER JOIN subprojects s ON p.id = s.subproject_id " +
                        "WHERE s.parent_project_id = ?")
                .param(parentProjectId)
                .query(Project.class)
                .list();
    }

    public List<Project> getAllSubProjects() {
        return jdbcClient.sql("SELECT * FROM PMTool.projects WHERE id IN (SELECT subproject_id FROM subprojects)")
                .query(Project.class)
                .list();
    }

    public int getParentIdBySubProjectId(int subProjectId) {
        String sql = "SELECT parent_project_id FROM PMTool.subprojects WHERE subproject_id = ?";
        List<Integer> result = jdbcClient.sql(sql)
                .param(subProjectId)
                .query(Integer.class)
                .list();
        return result.get(0);
    }
}