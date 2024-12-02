package kea.eksamen.model;

import kea.eksamen.dto.TeamMemberDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private int id;
    private int projectId;
    private int subProjectId;
    private String title;
    private String description;
    private TaskPriority priority;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int duration;
    private int userId;
    private TeamMemberDTO assignedUser;
    private int assignedUserId;


    public Task() {
    }

    public Task(int assignedUserId, TeamMemberDTO assignedUser, int userId, int duration, Date endDate, Date startDate, TaskPriority priority, String description, String title, int subProjectId, int projectId) {
        this.assignedUserId = assignedUserId;
        this.assignedUser = assignedUser;
        this.userId = userId;
        this.duration = duration;
        this.endDate = endDate;
        this.startDate = startDate;
        this.priority = priority;
        this.description = description;
        this.title = title;
        this.subProjectId = subProjectId;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", user=" + userId +
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}
