package kea.eksamen.repository;

import kea.eksamen.exceptions.TaskNotFoundExeption;
import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Repository
public class TaskRepository implements TaskRepositoryInterface {

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public Task addTask(Task task, int projectId) {
        System.out.println("adding task..");
        Date startDate = task.getStartDate();
        Date endDate = task.getEndDate();
        int duration = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

        Integer userId = task.getUserId();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = jdbcClient.sql("INSERT INTO PMTool.tasks (project_id, sub_project_id, title, description, priority, start_date, end_date, duration, user_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .param(projectId)
                .param(projectId)
                .param(task.getTitle())
                .param(task.getDescription())
                .param(task.getPriority().getDisplayName().toUpperCase())
                .param(task.getStartDate())
                .param(task.getEndDate())
                .param(duration)
                .param(userId)
                .update(keyHolder, "id");
        System.out.println("task added..");

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

        Date startDate = task.getStartDate();
        Date endDate = task.getEndDate();
        int duration = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));


        int rowsAffected = jdbcClient.sql("UPDATE PMTool.tasks " +
                        "SET title = ?, description = ?, priority = ?, start_date = ?, end_date = ?, duration = ? " +
                        "WHERE id = ?")
                .param(task.getTitle())
                .param(task.getDescription())
                .param(task.getPriority().getDisplayName().toUpperCase())
                .param(task.getStartDate())
                .param(task.getEndDate())
                .param(duration)
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
        if (deleteTask < 0) ;
        logger.info("Successfully deleted task with ID: " + deleteTask);
        return true;
    }


    @Override
    public Task findTaskById(int taskId) {
        String sql = "SELECT * FROM PMTool.tasks WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(taskId)
                .query(resultSet -> {
                    if (resultSet.next()){
                        Task task = new Task();
                        task.setId(resultSet.getInt("id"));
                        task.setProjectId(resultSet.getInt("project_id"));
                        task.setSubProjectId(resultSet.getInt("sub_project_id"));
                        task.setTitle(resultSet.getString("title"));
                        task.setDescription(resultSet.getString("description"));
                        task.setPriority(TaskPriority.valueOf(resultSet.getString("priority").toUpperCase()));
                        task.setStartDate(resultSet.getDate("start_date"));
                        task.setEndDate(resultSet.getDate("end_date"));
                        task.setDuration(resultSet.getInt("duration"));
                        task.setUserId(resultSet.getInt("user_id"));
                        return task;
                    }
                    return null;
                });

    }

    @Override
    public List<Task> getAllTasks(int projectId) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE project_id = ?")
                .param(projectId)
                .query((rs, rowNum) -> {
                    Task task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setProjectId(rs.getInt("project_id"));
                    task.setSubProjectId(rs.getInt("sub_project_id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setPriority(TaskPriority.valueOf(rs.getString("priority").toUpperCase()));
                    task.setStartDate(rs.getDate("start_date"));
                    task.setEndDate(rs.getDate("end_date"));
                    task.setDuration(rs.getInt("duration"));
                    task.setUserId(rs.getInt("user_id"));
                    return task;
                })
                .list();
    }
}
