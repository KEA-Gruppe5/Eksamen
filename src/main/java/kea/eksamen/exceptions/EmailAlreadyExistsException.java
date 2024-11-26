package kea.eksamen.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException() {
        super("User with this email is already registered.");
    }
}
