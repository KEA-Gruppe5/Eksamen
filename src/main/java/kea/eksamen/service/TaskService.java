package kea.eksamen.service;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.model.Task;
import kea.eksamen.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task addTask(Task task, int projectId){
        taskRepository.addTask(task,projectId);
        return task;
    }

    public List<Task> getAllTasks(int projectId){
        return taskRepository.getAllTasks(projectId);
    }

    public boolean deleteTask(int taskId){
        return taskRepository.deleteTask(taskId);
    }

    public Task editTask(Task task, int taskId){
        return taskRepository.updateTask(task, taskId);
    }

    public Task findTaskById(int taskId) {
        return taskRepository.findTaskById(taskId);
    }
}
