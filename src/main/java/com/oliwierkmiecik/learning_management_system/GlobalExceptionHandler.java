package com.oliwierkmiecik.learning_management_system;

import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(NullsForbiddenException.class)
    public ResponseEntity<String> handleNullsForbiddenException(NullsForbiddenException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
