package kea.eksamen.model;

import kea.eksamen.dto.TeamMemberDTO;
import java.util.Date;

public class Task {
    private int id;
    private int projectId;
    private int subProjectId;
    private String title;
    private String description;
    private TaskPriority priority;
    private int duration;
    private int userId;
    private TeamMemberDTO assignedUser;
    private int estimatedHours;
    private int assignedUserId;


    public Task() {
    }

    public Task(int projectId, int subProjectId, String title, String description, TaskPriority priority, int duration, int userId, TeamMemberDTO assignedUser, int estimatedHours, int assignedUserId) {
        this.projectId = projectId;
        this.subProjectId = subProjectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.duration = duration;
        this.userId = userId;
        this.assignedUser = assignedUser;
        this.estimatedHours = estimatedHours;
        this.assignedUserId = assignedUserId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", subProjectId=" + subProjectId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", duration=" + duration +
                ", userId=" + userId +
                ", assignedUser=" + assignedUser +
                ", assignedUserId=" + assignedUserId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public TeamMemberDTO getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(TeamMemberDTO assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
}
