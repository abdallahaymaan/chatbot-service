package com.pidima.chatbot.services;

import com.pidima.chatbot.models.ChatMessage;
import com.pidima.chatbot.models.ChatSession;
import com.pidima.chatbot.repositories.ChatRepository;
import com.pidima.chatbot.services.ai.AiProvider;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository repository;
    private final AiProvider aiProvider;

    public ChatService(ChatRepository repository, AiProvider aiProvider) {
        this.repository = repository;
        this.aiProvider = aiProvider;
    }

    public ChatSession createSession() {
        return repository.create();
    }

    public String sendMessage(String sessionId, String message) {
        ChatSession session = repository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        session.getMessages().add(new ChatMessage(sessionId, ChatMessage.Role.USER, message, Instant.now()));
        String reply = aiProvider.generateReply(session.getMessages(), message);
        session.getMessages().add(new ChatMessage(sessionId, ChatMessage.Role.ASSISTANT, reply, Instant.now()));
        repository.save(session);
        return reply;
    }

    public List<ChatMessage> getHistory(String sessionId) {
        ChatSession session = repository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));
        return session.getMessages();
    }
}


