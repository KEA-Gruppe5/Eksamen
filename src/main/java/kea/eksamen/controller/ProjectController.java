package kea.eksamen.controller;

import kea.eksamen.dto.SubprojectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.service.ProjectService;
import kea.eksamen.service.SubprojectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final SubprojectService subprojectService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    public ProjectController(ProjectService projectService, SubprojectService subprojectService) {
        this.projectService = projectService;
        this.subprojectService = subprojectService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "project/addProject";
    }

    @PostMapping("/add") //TODO: change to projects/add?
    public String addProject(@ModelAttribute Project project){
        projectService.addProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String listProjects(Model model, @RequestParam(required = false, defaultValue = "false") boolean archived) {
        List<Project> projects = archived ? projectService.getArchivedProjects() : projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("isArchived", archived);
        return "project/projects"; // Reuse the same template
    }


    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable("id") int id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/update")
    public String editProject(@PathVariable("id") int id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project/updateProject";
    }

    @PostMapping("/projects/{id}/update")
    public String updateProject(@PathVariable("id") int id, @ModelAttribute Project project) {
        projectService.updateProject(project, id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{parentId}/subprojects/add")
    public String showAddSubProjectForm(@PathVariable("parentId") int parentId, Model model) {
        model.addAttribute("parentId", parentId);
        model.addAttribute("project", new Project());
        return "project/addSubProject";
    }
    @PostMapping("/projects/{parentId}/subprojects/add")
    public String addSubProject(@PathVariable("parentId") int parentId, @ModelAttribute Project subProject) {
        Project createdSubProject = projectService.addProject(subProject);
        if (createdSubProject != null) {
            subprojectService.addSubProject(parentId, createdSubProject.getId()); //TODO: move the logic to service ?
        }
        return "redirect:/projects/" + parentId + "/subprojects";
    }

    @GetMapping("/projects/{id}/subprojects")
    public String listSubProjects(@PathVariable("id")int id, Model model) {
        System.out.println("Fetching subprojects for Parent Project ID: " + id);
        Project parentProject = projectService.getProjectById(id);
        List<SubprojectDTO> subProjects = subprojectService.getSubprojectDtosById(id);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("parentId", id);
        model.addAttribute("parentTitle", parentProject.getTitle());
        return "project/subProjects";
    }
    @PostMapping("/projects/{parentId}/subprojects/{subProjectId}")  //TODO:possible to remove and use deleteProject
    public String removeSubProject(@PathVariable("parentId")int parentId, @PathVariable int subProjectId) {
        subprojectService.removeSubProject(parentId, subProjectId);
        return "redirect:/projects/" + parentId + "/subprojects";
    }


    @PostMapping("/projects/{id}/archive")
    public String archiveProject(@PathVariable int id) {
        projectService.archiveProject(id);
        return "redirect:/projects";
    }

    @PostMapping("/projects/{id}/unarchive")
    public String unarchiveProject(@PathVariable int id) {
        projectService.unarchiveProject(id);
        return "redirect:/projects?archived=true"; // Correct redirection
    }



}
