package kea.eksamen.controller;

import kea.eksamen.service.ProjectTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;

    public ProjectTeamController(ProjectTeamService projectTeamService) {
        this.projectTeamService = projectTeamService;
    }

    @GetMapping("/projects/{projectId}/team")
    public String getTeamMembers(@PathVariable int projectId, Model model){
        model.addAttribute("team", projectTeamService.getTeamMembers(projectId));
        return "project/projectTeam";
    }
}
