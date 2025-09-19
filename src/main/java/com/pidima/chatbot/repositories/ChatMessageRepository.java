package com.pidima.chatbot.repositories;

import com.pidima.chatbot.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);
    
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.sessionId = :sessionId ORDER BY cm.timestamp ASC")
    List<ChatMessage> findMessagesBySessionId(@Param("sessionId") String sessionId);
    
    void deleteBySessionId(String sessionId);
}
