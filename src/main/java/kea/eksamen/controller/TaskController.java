package kea.eksamen.controller;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.model.Task;
import kea.eksamen.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{projectId}/add")
    public String addTask(Model model, @PathVariable int projectId){
        model.addAttribute("addNewTask", new Task());
        model.addAttribute("projectId", projectId);
        return "task/addTask";
    }

    @PostMapping("/{projectId}/add")
    public String addTask( @PathVariable int projectId, @ModelAttribute Task task){

        //TODO instead of projectId in the path, get projectId directly from project class
        taskService.addTask(task, projectId);
        return "task/tasks";
    }
}
