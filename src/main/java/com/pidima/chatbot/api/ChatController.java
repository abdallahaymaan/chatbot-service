package com.pidima.chatbot.api;

import com.pidima.chatbot.api.dto.CreateSessionResponse;
import com.pidima.chatbot.api.dto.HistoryResponse;
import com.pidima.chatbot.api.dto.SendMessageRequest;
import com.pidima.chatbot.api.dto.SendMessageResponse;
import com.pidima.chatbot.services.ChatService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@Validated
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/session")
    public ResponseEntity<CreateSessionResponse> createSession() {
        var session = chatService.createSession();
        log.info("Created session {}", session.getSessionId());
        return ResponseEntity.ok(new CreateSessionResponse(session.getSessionId()));
    }

    @PostMapping("/message")
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        String reply = chatService.sendMessage(request.getSessionId(), request.getMessage());
        return ResponseEntity.ok(new SendMessageResponse(request.getSessionId(), reply));
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<HistoryResponse> history(@PathVariable("sessionId") String sessionId) {
        var messages = chatService.getHistory(sessionId);
        return ResponseEntity.ok(new HistoryResponse(sessionId, messages));
    }
}


