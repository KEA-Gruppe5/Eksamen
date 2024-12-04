package kea.eksamen.repository;

import kea.eksamen.exceptions.TaskNotFoundExeption;
import kea.eksamen.model.Task;
import kea.eksamen.util.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Repository
public class TaskRepository implements TaskRepositoryInterface {

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public Task addTask(Task task) {
        logger.info("adding task..");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = jdbcClient.sql("INSERT INTO PMTool.tasks (project_id, title, description, priority, status, deadline, estimated_hours) " +
                        "VALUES (?, ?, ?, ?, ?,?, ?)")
                .param(task.getProjectId())
                .param(task.getTitle())
                .param(task.getDescription())
                .param(task.getPriority().getId())
                .param(task.getStatus().getId())
                .param(task.getDeadline())
                .param(task.getEstimatedHours())
                .update(keyHolder, "id");

        if (id != 0 && keyHolder.getKey() != null) {
            task.setId(keyHolder.getKey().intValue());
            logger.info("Successfully added new task: " + task);
            return task;
        } else {
            logger.error("Failed to add task: " + task);

        }
        return task;
    }

    @Override
    public Task updateTask(Task task, int taskId) {
        logger.info("Updating task with ID: " + taskId);
        int rowsAffected = jdbcClient.sql("UPDATE PMTool.tasks " +
                        "SET title = ?, description = ?, priority = ?, status = ?, deadline = ?, estimated_hours = ? " +
                        "WHERE id = ?")
                .param(task.getTitle())
                .param(task.getDescription())
                .param(task.getPriority().getId())
                .param(task.getStatus().getId())
                .param(task.getDeadline())
                .param(task.getEstimatedHours())
                .param(taskId)
                .update();

        if (rowsAffected > 0) {
            logger.info("Successfully updated task with ID: " + taskId);
            return task;
        } else {
            logger.warn("No task found with ID: " + taskId);
            throw new IllegalStateException("Failed to update task. Task ID might not exist: " + taskId);
        }
    }


    @Override
    public boolean deleteTask(int taskId) {
        int deleteTask = jdbcClient.sql("DELETE FROM tasks WHERE id = ?")
                .param(taskId)
                .update();
        if (deleteTask > 0) {
            logger.info("Successfully deleted task with ID: " + deleteTask);
            return true;
        }
        logger.warn("No task found with ID: " + taskId + ". Deletion failed.");
        return false;
    }


    @Override
    public Task findTaskById(int taskId) {
        String sql = "SELECT * FROM PMTool.tasks WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(taskId)
                .query(new TaskMapper())
                .optional()
                .orElseThrow(TaskNotFoundExeption::new);
    }

    @Override
    public List<Task> getAllTasks(int projectId) {
        return jdbcClient.sql("SELECT * FROM PMTool.tasks t WHERE t.project_id = ?")
                .param(projectId)
                .query(new TaskMapper())
                .list();
    }

    public void assignMember(int taskId, int userid) {
        String sql = "UPDATE tasks SET assigned_user_id = ? WHERE id = ?";
        jdbcClient.sql(sql)
                .param(1, userid)
                .param(2, taskId)
                .update();

    }

    public void removeAssignedUser(int taskId) {
        String sql = "UPDATE tasks SET assigned_user_id = NULL WHERE id = ?";
        jdbcClient.sql(sql)
                .param(taskId)
                .update();
    }

    public int findAssignedMember(int taskId){
        String sql = "SELECT assigned_user_id FROM tasks WHERE id = ?";
        List<Integer> result = jdbcClient.sql(sql)
                .param(taskId)
                .query(Integer.class)
                .list();
        if (result.isEmpty() || result.get(0) == null) {
            return 0;
        }
        return result.get(0);
    }

    public double getHoursForAllTasks(int subprojectId) {
        String sql = "SELECT SUM(estimated_hours) FROM PMTool.tasks WHERE project_id = ?";
        return jdbcClient.sql(sql)
                .param(subprojectId)
                .query(Double.class).optional().orElse(0.0);
    }

    public List<Task> getAllTasksByProjectId(int projectId) {
        String sql = "SELECT * FROM PMTool.tasks WHERE project_id = ?";
        return jdbcClient.sql(sql)
                .param(projectId)
                .query(new TaskMapper())
                .list();
    }

}
