package kea.eksamen.controller;

import jakarta.validation.Valid;
import kea.eksamen.dto.ProjectDTO;
import kea.eksamen.model.Project;
import kea.eksamen.service.ProjectService;
import kea.eksamen.service.SubprojectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
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
        model.addAttribute("project", new ProjectDTO());
        return "project/addProject";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute("project") @Valid ProjectDTO project, BindingResult bindingResult){
        logger.info("Does binding result has errors? : "+bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            return "project/addProject";
        }
        projectService.addProject(project);
        return "redirect:/projects";
    }


    @GetMapping("")
    public String listProjects(Model model, @RequestParam(required = false, defaultValue = "false") boolean archived) {
        // Get the relevant projects (archived or active)
        List<ProjectDTO> allProjects;
        if (archived) allProjects = projectService.getArchivedProjects();
        else allProjects = projectService.getAllProjects();

        // Filter to include only parent projects (exclude subprojects)
        List<ProjectDTO> parentProjects = new ArrayList<>();  //TODO:move to service
        for (ProjectDTO project : allProjects) {
            if (projectService.isParentProject(project.getId())) {
                parentProjects.add(project);
            }
        }

        // Add attributes to the model
        model.addAttribute("projects", parentProjects);
        model.addAttribute("isArchived", archived);

        // Return the view
        return "project/projects";
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable("id") int id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/{id}/update")
    public String editProject(@PathVariable("id") int id, Model model) {
        ProjectDTO project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project/updateProject";
    }

    @PostMapping("/{id}/update")
    public String updateProject(@PathVariable("id") int id, @ModelAttribute("project") @Valid ProjectDTO project,
                                BindingResult bindingResult) {
        logger.info("Does binding result has errors? : "+bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            return "project/updateProject";
        }
        projectService.updateProject(project, id);
        return "redirect:/projects";
    }

    @GetMapping("/{parentId}/subprojects/add")
    public String showAddSubProjectForm(@PathVariable("parentId") int parentId, Model model) {
        model.addAttribute("parentId", parentId);
        model.addAttribute("project", new ProjectDTO());
        return "project/addSubProject";
    }

    @PostMapping("/{parentId}/subprojects/add")
    public String addSubProject(@PathVariable("parentId") int parentId,  @ModelAttribute("project") @Valid ProjectDTO subProject, BindingResult bindingResult) {
        logger.info("Does binding result has errors? : "+bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            return "project/addSubProject";
        }
        subprojectService.addSubProject(subProject, parentId);
        return "redirect:/projects/" + parentId + "/subprojects";
    }

    @GetMapping("/{id}/subprojects")
    public String listSubProjects(@PathVariable("id")int id, Model model) {
        System.out.println("Fetching subprojects for Parent Project ID: " + id);
        ProjectDTO parentProject = projectService.getProjectById(id);
        List<ProjectDTO> subProjects = subprojectService.getProjectDtosById(id);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("parentId", id);
        model.addAttribute("parentTitle", parentProject.getTitle());
        return "project/subProjects";
    }

    @PostMapping("/{parentId}/subprojects/{subProjectId}/remove")
    public String removeSubProject(@PathVariable("parentId") int parentId, @PathVariable("subProjectId") int subProjectId) {
        projectService.deleteProject(subProjectId);
        return "redirect:/projects/" + parentId + "/subprojects";
    }

    @PostMapping("/{id}/archive")
    public String archiveProject(@PathVariable int id) {
        projectService.archiveProject(id);
        return "redirect:/projects";
    }

    @PostMapping("/{id}/unarchive")
    public String unarchiveProject(@PathVariable int id) {
        projectService.unarchiveProject(id);
        return "redirect:/projects?archived=true";
    }


}
