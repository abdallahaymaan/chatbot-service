package com.pidima.chatbot.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendMessageRequest {
    @NotBlank
    private String sessionId;

    @NotBlank
    @Size(max = 8000)
    private String message;

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}


