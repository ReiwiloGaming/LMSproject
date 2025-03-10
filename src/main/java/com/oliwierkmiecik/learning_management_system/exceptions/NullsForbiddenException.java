package com.oliwierkmiecik.learning_management_system.exceptions;

public class NullsForbiddenException extends RuntimeException {
    public NullsForbiddenException(String message) {
        super (message);
    }
}
