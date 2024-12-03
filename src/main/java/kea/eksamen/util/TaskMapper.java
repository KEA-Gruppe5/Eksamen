package kea.eksamen.util;

import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.User;
import kea.eksamen.repository.UserRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Task task = new Task(
                resultSet.getInt("id"),
                resultSet.getInt("project_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                TaskPriority.getEnumFromId(resultSet.getInt("priority")),
                resultSet.getInt("assigned_user_id"),
                resultSet.getDouble("estimated_hours"));

        return task;
    }
}

