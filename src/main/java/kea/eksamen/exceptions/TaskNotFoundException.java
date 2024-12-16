package kea.eksamen.exceptions;

public class TaskNotFoundException extends RuntimeException {
    /**
     * Class created by: Kristoffer
     */
    public TaskNotFoundException() {
        super("Task not found");
    }
}
