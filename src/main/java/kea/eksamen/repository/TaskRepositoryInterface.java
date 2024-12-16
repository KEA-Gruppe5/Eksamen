package kea.eksamen.repository;

import kea.eksamen.model.Task;

import java.util.List;

public interface TaskRepositoryInterface {
    /**
     * Class created by: Kristoffer
     */
    Task addTask (Task task);
    Task updateTask(Task task, int taskId);
    boolean deleteTask(int taskId);
    Task findTaskById(int taskId);
    List<Task> getAllTasks(int projectId);

}
