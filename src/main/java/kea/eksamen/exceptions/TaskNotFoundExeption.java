package kea.eksamen.exceptions;

public class TaskNotFoundExeption extends RuntimeException {
    public TaskNotFoundExeption() {
        super("Task not found");
    }
}
