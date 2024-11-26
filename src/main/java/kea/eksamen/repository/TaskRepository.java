package kea.eksamen.repository;

import kea.eksamen.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository implements TaskRepositoryInterface{

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    private final JdbcClient jdbcClient;
    public TaskRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }



    @Override
    public Task addTask(Task task, int projectId) {
        int id = jdbcClient.sql("INSERT INTO tasks (title, start_date, end_date, duration, user_id) VALUES (?, ?, ?, ?, ?)")
                .param(task.getTitle())
                .param(task.getStartDate())
                .param(task.getStartDate())
                .param(task.getDuration())
                .param(task.getUser())
                .update();

        if(id !=0){
            task.setId(id);
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
        return null;
    }
}
