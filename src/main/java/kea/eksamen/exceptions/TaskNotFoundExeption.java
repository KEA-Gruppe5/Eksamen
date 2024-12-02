package kea.eksamen.exceptions;

public class TaskNotFoundExeption extends RuntimeException {
    public TaskNotFoundExeption(String message) {
        super("Task not found");
    }
}
