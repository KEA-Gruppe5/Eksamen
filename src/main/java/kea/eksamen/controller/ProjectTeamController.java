package kea.eksamen.controller;

import jakarta.servlet.http.HttpSession;
import kea.eksamen.service.ProjectTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectTeamController.class);

    public ProjectTeamController(ProjectTeamService projectTeamService) {
        this.projectTeamService = projectTeamService;
    }

    @GetMapping("/projects/{projectId}/team")
    public String getTeamMembers(@PathVariable int projectId, Model model, HttpSession session){
        if (session.getAttribute("userId") == null) {
            return "unauthorized";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("newTeamMembers", new ArrayList<>());
        model.addAttribute("team", projectTeamService.findTeamMembers(projectId));
        model.addAttribute("allUsers", projectTeamService.findUnassignedUsers(projectId));
        return "project/projectTeam";
    }

    @PostMapping("/projects/{projectId}/team")
    public String assignUserToProject(@PathVariable int projectId, @RequestParam("newTeamMembers") ArrayList<Integer> newTeamMembersIds){
        logger.info("newTeamMembersIds size is " + newTeamMembersIds.size());
        projectTeamService.assignUserToProject(projectId, newTeamMembersIds);
        return "redirect:/projects/" + projectId + "/team";
    }

    @DeleteMapping("/projects/{projectId}/team/{userId}")
    public String removeUserFromProject(@PathVariable int userId, @PathVariable int projectId) {
        projectTeamService.removeUserFromProject(userId, projectId);
        return "redirect:/projects/" + projectId + "/team";
    }
}
