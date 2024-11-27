package kea.eksamen.model;

public enum TaskPriority {
    LOW("Low",1),
    MEDIUM("Medium",2),
    HIGH("High",3);

    private final String displayName;
    private final int id;

    TaskPriority(String displayName, int id){
        this.displayName = displayName;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }
}
