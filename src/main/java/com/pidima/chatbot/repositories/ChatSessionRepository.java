package com.pidima.chatbot.repositories;

import com.pidima.chatbot.models.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, String> {
    
    @Query("SELECT cs FROM ChatSession cs LEFT JOIN FETCH cs.messages WHERE cs.sessionId = :sessionId")
    Optional<ChatSession> findByIdWithMessages(@Param("sessionId") String sessionId);
    
    @Query("SELECT cs FROM ChatSession cs ORDER BY cs.createdAt DESC")
    List<ChatSession> findAllOrderByCreatedAtDesc();
}
