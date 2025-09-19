package com.pidima.chatbot.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pidima.chatbot.services.ChatService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChatController.class)
class ValidationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ChatService chatService;

    @Test
    void sendMessage_missingFields_returns400() throws Exception {
        var payload = objectMapper.writeValueAsString(new java.util.LinkedHashMap<>() {{
            put("sessionId", "");
            put("message", "");
        }});
        mockMvc.perform(post("/chat/message").contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isBadRequest());
    }
}


