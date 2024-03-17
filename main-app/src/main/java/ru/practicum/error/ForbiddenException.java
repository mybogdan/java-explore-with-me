package ru.practicum.error;

import lombok.Data;

@Data
public class ForbiddenException extends RuntimeException {

    private final String message;
    private final String reason;

    public ForbiddenException(String message, String reason) {
        super();
        this.message = message;
        this.reason = reason;
    }
}
