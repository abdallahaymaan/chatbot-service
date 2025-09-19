package com.pidima.chatbot.models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    public enum Role { USER, ASSISTANT, SYSTEM }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", nullable = false)
    private String sessionId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private ChatSession chatSession;

    public ChatMessage() {}

    public ChatMessage(Role role, String content, Instant timestamp) {
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
    }
    
    public ChatMessage(String sessionId, Role role, String content, Instant timestamp) {
        this.sessionId = sessionId;
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    
    public ChatSession getChatSession() { return chatSession; }
    public void setChatSession(ChatSession chatSession) { this.chatSession = chatSession; }
}


