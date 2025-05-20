package org.example.lostandfound.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvisor {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleGeneral(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong: " + ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleGeneral(AuthenticationException ex) {
        return ResponseEntity.status(403)
                .body("Something went wrong: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(404)
                .body("Internal server error: " + ex.getMessage());
    }
}
