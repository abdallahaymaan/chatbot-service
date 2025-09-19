package com.pidima.chatbot.api.dto;

import com.pidima.chatbot.models.ChatMessage;
import java.util.List;

public class HistoryResponse {
    private String sessionId;
    private List<ChatMessage> messages;

    public HistoryResponse() {}
    public HistoryResponse(String sessionId, List<ChatMessage> messages) {
        this.sessionId = sessionId;
        this.messages = messages;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }
}


