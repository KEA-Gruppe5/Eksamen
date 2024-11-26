package kea.eksamen.controller;

import kea.eksamen.model.Project;
import kea.eksamen.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

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
    public String addProject(@ModelAttribute Project project) throws SQLException {
        projectService.addProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAllProjects());
        return "project/projects";
    }






}
