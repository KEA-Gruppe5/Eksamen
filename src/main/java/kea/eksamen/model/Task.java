package kea.eksamen.model;

public class Task {
    private int id;
    private int projectId;
    private String title;
    private String description;
    private TaskPriority priority;
    private int assignedUserId;
    private double estimatedHours;


    public Task() {
    }

    public Task(int id, int projectId, String title, String description,
                TaskPriority priority, int assignedUserId, double estimatedHours) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
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

    public Task(int projectId, String title, String description, TaskPriority priority, int assignedUserId, int estimatedHours) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignedUserId = assignedUserId;
        this.estimatedHours = estimatedHours;
    }
}
