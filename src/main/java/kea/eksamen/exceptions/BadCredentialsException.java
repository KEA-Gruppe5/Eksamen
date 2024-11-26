package kea.eksamen.exceptions;

public class BadCredentialsException extends RuntimeException{
    public BadCredentialsException() {
        super("Invalid email or password.");
    }
}
