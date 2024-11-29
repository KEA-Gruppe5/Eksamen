package kea.eksamen.controller;

import kea.eksamen.model.Project;
import kea.eksamen.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "project/addProject";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute Project project){
        projectService.addProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String listProjects(Model model){
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/projects";
    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/update")
    public String editProject(@PathVariable int id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project/updateProject";
    }

    @PostMapping("/projects/{id}/update")
    public String updateProject(@PathVariable int id, @ModelAttribute Project project) {
        projectService.updateProject(project, id);
        return "redirect:/projects";
    }

    // Add subproject
    @PostMapping("/projects/{parentId}/subprojects/add")
    public String addSubProject(@PathVariable int parentId, @RequestParam int subProjectId) {
        projectService.addSubProject(parentId, subProjectId);
        return "redirect:/projects/" + parentId + "/subprojects"; // Correct URL path
    }
    // List subprojects
    @GetMapping("/projects/{id}/subprojects")
    public String listSubProjects(@PathVariable int id, Model model) {
        List<Project> subProjects = projectService.getSubProjectsByParentId(id);
        model.addAttribute("subProjects", subProjects); // Ensure model name matches Thymeleaf
        model.addAttribute("parentId", id);
        return "project/subProjects"; // Template for listing subprojects
    }
    // Remove subproject
    @PostMapping("/projects/{parentId}/subprojects/{subProjectId}/remove")
    public String removeSubProject(@PathVariable int parentId, @PathVariable int subProjectId) {
        projectService.removeSubProject(parentId, subProjectId);
        return "redirect:/projects/" + parentId + "/subprojects"; // Correct URL path
    }
}
