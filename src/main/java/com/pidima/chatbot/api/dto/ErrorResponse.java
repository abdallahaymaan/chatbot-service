package com.pidima.chatbot.api.dto;

import java.time.Instant;

public class ErrorResponse {
    private Instant timestamp = Instant.now();
    private String path;
    private String message;
    private String code;

    public ErrorResponse() {}

    public ErrorResponse(String path, String message, String code) {
        this.path = path;
        this.message = message;
        this.code = code;
    }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}


