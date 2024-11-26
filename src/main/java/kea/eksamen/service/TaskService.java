package kea.eksamen.service;

import kea.eksamen.model.Task;
import kea.eksamen.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public void addTask(Task task, int projectId){
        taskRepository.addTask(task,projectId);
    }
}
