package kea.eksamen.util;

import kea.eksamen.model.Task;
import kea.eksamen.model.TaskPriority;
import kea.eksamen.model.TaskStatus;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TaskMapper implements RowMapper<Task> {
    /**
     * Class created by: Kristoffer
     */

    @Override
    public Task mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return new Task(
                resultSet.getInt("id"),
                resultSet.getInt("project_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                TaskPriority.getEnumFromId(resultSet.getInt("priority")),
                TaskStatus.getEnumFromId(resultSet.getInt("status")),
                resultSet.getDate("deadline").toLocalDate(),
                resultSet.getInt("assigned_user_id"),
                resultSet.getDouble("estimated_hours"));
    }
}

