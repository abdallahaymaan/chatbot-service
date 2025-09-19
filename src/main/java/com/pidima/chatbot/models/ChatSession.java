package com.pidima.chatbot.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_sessions")
public class ChatSession {
    @Id
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    @OneToMany(mappedBy = "sessionId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("timestamp ASC")
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}


