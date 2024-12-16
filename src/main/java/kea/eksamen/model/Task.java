package kea.eksamen.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {
    /**
     * Class & methods created by: Kristoffer
     */
    private int id;
    private int projectId;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // formats deadline in thymeleaf to use correct formatting
    private LocalDate deadline;
    private int assignedUserId;
    private double estimatedHours;


    public Task() {
    }

    public Task(int id, int projectId, String title, String description,
                TaskPriority priority, TaskStatus status, LocalDate deadline, int assignedUserId, double estimatedHours) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.assignedUserId = assignedUserId;
        this.estimatedHours = estimatedHours;
    }

    public Task(int projectId, String title, String description, TaskPriority priority, TaskStatus status, LocalDate deadline, int assignedUserId, int estimatedHours) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.assignedUserId = assignedUserId;
        this.estimatedHours = estimatedHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
