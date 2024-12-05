package kea.eksamen.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e, Model model) {
        logger.error("Null Pointer Exception: " + e.getMessage(), e);
        model.addAttribute("errorType", "Null Pointer Exception");
        model.addAttribute("errorMessage", "Something went wrong. Please contact support if the problem persists.");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }
    // 2. Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        logger.error("Illegal Argument Exception: " + e.getMessage(), e);
        model.addAttribute("errorType", "Invalid Argument");
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }
    // 3. Handle Database Errors
    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(DataAccessException e, Model model) {
        logger.error("Database Error: " + e.getMessage(), e);
        model.addAttribute("errorType", "Database Error");
        model.addAttribute("errorMessage", "A database error occurred. Please try again later.");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }


}
