package kea.eksamen.model;

public enum TaskStatus {
    /**
     * Class created by: Kristoffer
     */
    TODO("Todo",1),
    IN_PROGRESS("In Progress",2),
    COMPLETED("Completed",3),
    CANCELED("Canceled",4),
    PENDING("Pending",5);

    private final String displayName;
    private final int id;

    TaskStatus(String displayName, int id){
        this.displayName = displayName;
        this.id = id;
    }

    public static TaskStatus getEnumFromId(int id){
        for(TaskStatus taskPriority : TaskStatus.values()){
            if(taskPriority.getId() == id){
                return taskPriority;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }
}
