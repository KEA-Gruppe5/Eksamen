package kea.eksamen.dto;


public class TaskDTO {
    private int id;
    private int projectId;
    private String title;
    private String description;
    private String priority;
    private TeamMemberDTO teamMemberDTO;
    private double estimatedHours;

    public TaskDTO(int id, int projectId, String title, String description, String priority, TeamMemberDTO teamMemberDTO, double estimatedHours) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.teamMemberDTO = teamMemberDTO;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public TeamMemberDTO getTeamMemberDTO() {
        return teamMemberDTO;
    }

    public void setTeamMemberDTO(TeamMemberDTO teamMemberDTO) {
        this.teamMemberDTO = teamMemberDTO;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", teamMemberDTO=" + teamMemberDTO +
                ", estimatedHours=" + estimatedHours +
                '}';
    }
}
