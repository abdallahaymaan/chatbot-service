package com.pidima.chatbot.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pidima.chatbot.services.ChatService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ChatController.class, HealthController.class, GlobalExceptionHandler.class})
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatService chatService;

    private String sessionId = "test-session";

    @BeforeEach
    void setup() {}

    @Test
    void createSession_ok() throws Exception {
        var session = new com.pidima.chatbot.models.ChatSession();
        session.setSessionId(sessionId);
        Mockito.when(chatService.createSession()).thenReturn(session);
        mockMvc.perform(post("/chat/session"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sessionId").value(sessionId));
    }

    @Test
    void sendMessage_ok() throws Exception {
        Mockito.when(chatService.sendMessage(Mockito.eq(sessionId), Mockito.eq("hi"))).thenReturn("hello");
        var payload = objectMapper.writeValueAsString(new java.util.LinkedHashMap<>() {{
            put("sessionId", sessionId);
            put("message", "hi");
        }});
        mockMvc.perform(post("/chat/message").contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sessionId").value(sessionId))
            .andExpect(jsonPath("$.reply").value("hello"));
    }

    @Test
    void history_ok() throws Exception {
        Mockito.when(chatService.getHistory(Mockito.eq(sessionId))).thenReturn(List.of());
        mockMvc.perform(get("/chat/history/" + sessionId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sessionId").value(sessionId))
            .andExpect(jsonPath("$.messages").isArray());
    }

    @Test
    void health_ok() throws Exception {
        mockMvc.perform(get("/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"));
    }
}


