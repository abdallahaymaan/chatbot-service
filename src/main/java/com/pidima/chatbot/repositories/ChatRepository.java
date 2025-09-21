package com.pidima.chatbot.repositories;

import com.pidima.chatbot.models.ChatSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatRepository {
    
    private final ChatSessionRepository chatSessionRepository;
    
    public ChatRepository(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }

    public ChatSession create() {
        ChatSession session = new ChatSession();
        return chatSessionRepository.save(session);
    }

    public Optional<ChatSession> findById(String sessionId) {
        return chatSessionRepository.findById(sessionId);
    }
    
    public Optional<ChatSession> findByIdWithMessages(String sessionId) {
        return chatSessionRepository.findByIdWithMessages(sessionId);
    }

    public ChatSession save(ChatSession session) {
        return chatSessionRepository.save(session);
    }

    public List<ChatSession> findAll() { 
        return chatSessionRepository.findAllOrderByCreatedAtDesc();
    }
    
    public void delete(String sessionId) {
        chatSessionRepository.deleteById(sessionId);
    }
}


