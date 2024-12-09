package kea.eksamen.controller;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.dto.TaskDTO;
import kea.eksamen.model.Task;
import kea.eksamen.service.SubprojectService;
import kea.eksamen.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/project")
public class TaskController {
    private final TaskService taskService;
    private final SubprojectService subprojectService;


    public TaskController(TaskService taskService, SubprojectService subprojectService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
    }

    @GetMapping("/{projectId}/add")
    public String addTask(Model model, @PathVariable("projectId") int projectId, HttpSession session){
        if (session.getAttribute("userId") == null) {
            return "unauthorized";
        }
        Task task = new Task();
        task.setProjectId(projectId); // Pre-set the projectId
        model.addAttribute("addNewTask", task);
        model.addAttribute("projectId", projectId);
        return "task/addTask";
    }
    @PostMapping("/{projectId}/add")
    public String addTask(@ModelAttribute Task task){
        taskService.addTask(task);
        System.out.println("Redirecting to: /task/" + task.getProjectId() + "/tasks");
        return "redirect:/project/" + task.getProjectId() +"/tasks";
    }

    @GetMapping("/{projectId}/tasks")
    public String viewTasks(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "unauthorized";
        }
        ProjectDTO subProjectTitle = subprojectService.getProjectById(projectId);
        List<TaskDTO> tasks = taskService.getTaskDtosByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        model.addAttribute("projectTitle",subProjectTitle.getTitle());

        return "task/tasks";
    }

    @PostMapping("/{projectId}/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId")int taskId, @PathVariable("projectId") int projectId){
        taskService.deleteTask(taskId);
        return "redirect:/project/" + projectId +"/tasks";
    }

    @GetMapping("/{projectId}/{taskId}/edit")
    public String editTask(@PathVariable("projectId") int projectId,
                           @PathVariable("taskId") int taskId, Model model, HttpSession session){
        if (session.getAttribute("userId") == null) {
            return "unauthorized";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("editTask", taskService.findTaskById(taskId));


        return "task/editTask";
    }

    @PostMapping("/{projectId}/{taskId}/edit")
    public String editTask(@PathVariable("taskId")int taskId,
                           @PathVariable("projectId") int projectId, @ModelAttribute Task task){
        taskService.editTask(task, taskId);
        return "redirect:/project/" + projectId +"/tasks";
    }

    @GetMapping("/{projectId}/{taskId}/assign")
    public String assignMember(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId,
                               Model model, HttpSession session){

        if (session.getAttribute("userId") == null) {
            return "unauthorized";
        }

        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("assignedMember", taskService.getAssignedMember(taskId));
        model.addAttribute("users", taskService.getMembersFromTeam(subprojectService.getParentId(projectId)));

        return "task/assignTask";
    }

    @PostMapping("/{projectId}/{taskId}/assign")
    public String assignTeamMemberToTask(@PathVariable("projectId") int projectId,
                                         @PathVariable("taskId") int taskId, @RequestParam("userIdToAssign") int userIdToAssign)  {
        taskService.assignMemberToTask(taskId, userIdToAssign);
        return "redirect:/project/" + projectId +"/tasks";
    }

    @PostMapping("/{projectId}/{taskId}/removeMember")
    public String removeAssignedUser(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        taskService.removeAssignedUser(taskId);
        return "redirect:/project/" + projectId +"/tasks";
    }


}
