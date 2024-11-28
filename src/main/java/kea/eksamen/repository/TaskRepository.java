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

import java.util.List;

@Repository
public class TaskRepository implements TaskRepositoryInterface{

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    private final JdbcClient jdbcClient;
    public TaskRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }



    @Override
    public Task addTask(Task task, int projectId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = jdbcClient.sql("INSERT INTO tasks (project_id, sub_project_id, title, description, priority, start_date, end_date, duration, user_id) VALUES (?, ?, ?, ?, ?,?,?,?,?)")
                .param(projectId)
                .param(projectId)
                .param(task.getTitle())
                .param(task.getDescription())
                .param(task.getPriority().getDisplayName().toUpperCase())
                .param(task.getStartDate())
                .param(task.getEndDate())
                .param(task.getDuration())
                .param(task.getUserId())
                .update(keyHolder, "id");

        if(id !=0 && keyHolder.getKey()!=null){
            task.setId(keyHolder.getKey().intValue());
            logger.info("add new task: " + task);
            return task;
        }
        return task;
    }

    @Override
    public Task updateTask(Task task, int taskId) {
        return null;
    }

    @Override
    public boolean deleteTask(int taskId) {
        return false;
    }

    @Override
    public Task findTaskById(int taskId) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE id = ?")
                .param(taskId)
                .query(Task.class)
                .optional()
                .orElseThrow(() -> new TaskNotFoundExeption("Task not found with ID: " + taskId));
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }
}
