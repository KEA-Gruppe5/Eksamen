package kea.eksamen.exceptions;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, HttpSession httpSession) {
        logger.error("An error occurred: " + e.getMessage(), e);  // Log the error
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());

        return "error";  // Redirect to error.html
    }

}
