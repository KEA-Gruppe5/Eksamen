package kea.eksamen.controller;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.model.Task;
import kea.eksamen.model.User;
import kea.eksamen.service.TaskService;
import kea.eksamen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;


    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{projectId}/add")
    public String addTask(Model model, @PathVariable("projectId") int projectId){
        model.addAttribute("addNewTask", new Task());
        model.addAttribute("projectId", projectId);
        return "task/addTask";
    }
    @PostMapping("/{projectId}/add")
    public String addTask(HttpSession session, @PathVariable("projectId") int projectId, @ModelAttribute Task task){
        Integer userId = (Integer) session.getAttribute("userId");
        task.setUserId(userId);

        //TODO instead of projectId in the path, get projectId directly from project class
        taskService.addTask(task, projectId);
        System.out.println("Redirecting to: /task/" + projectId + "/tasks");
        return "redirect:/task/" + projectId +"/tasks";
    }

    @GetMapping("/{projectId}/tasks")
    public String viewTasks(@PathVariable("projectId") int projectId, Model model) {
        List<Task> tasks = taskService.getAllTasks(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        return "task/tasks";
    }

    @PostMapping("/{projectId}/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId")int taskId, @PathVariable("projectId") int projectId){
        taskService.deleteTask(taskId);
        return "redirect:/task/" + projectId +"/tasks";
    }

    @GetMapping("/{projectId}/{taskId}/edit")
    public String editTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId, Model model){
        model.addAttribute("projectId", projectId);
        model.addAttribute("editTask", taskService.findTaskById(taskId));
        return "task/editTask";
    }

    @PostMapping("/{projectId}/{taskId}/edit")
    public String editTask(@PathVariable("taskId")int taskId, @PathVariable("projectId") int projectId, @ModelAttribute Task task){
        taskService.editTask(task, taskId);
        return "redirect:/task/" + projectId +"/tasks";
    }

    @GetMapping("/{projectId}/{taskId}/assign")
    public String assignMember(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId, Model model){
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "task/assignTask";
    }

    @PostMapping("/{projectId}/{taskId}/assign")
    public String assignTeamMemberToTask(@PathVariable("projectId") int projectId,
                                         @PathVariable("taskId") int taskId, @RequestParam("userIdToAssign") int userIdToAssign)  {
        taskService.assignMemberToTask(taskId, userIdToAssign);
        return "redirect:/task/" + projectId +"/tasks";
    }
}
