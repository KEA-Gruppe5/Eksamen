package kea.eksamen.controller;

import kea.eksamen.service.ProjectTeamService;
import kea.eksamen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectTeamController.class);

    public ProjectTeamController(ProjectTeamService projectTeamService, UserService userService) {
        this.projectTeamService = projectTeamService;
        this.userService = userService;
    }

    @GetMapping("/projects/{projectId}/team")
    public String getTeamMembers(@PathVariable int projectId, Model model){
        model.addAttribute("projectId", projectId);
        model.addAttribute("newTeamMembers", new ArrayList<>());
        model.addAttribute("team", projectTeamService.getTeamMembers(projectId));
        model.addAttribute("allUsers", userService.findAllUsers());
        return "project/projectTeam";
    }

    @PostMapping("/projects/{projectId}/team")
    public String addTeamMember(@PathVariable int projectId, @RequestParam("newTeamMembers") ArrayList<Integer> newTeamMembersIds){
        logger.info("newTeamMembersIds size is " + newTeamMembersIds.size());
        projectTeamService.addTeamMember(projectId, newTeamMembersIds);
        return "redirect:/projects/" + projectId + "/team";
    }
}
