package com.pidima.chatbot.services.ai;

import com.pidima.chatbot.models.ChatMessage;
import java.util.List;

public interface AiProvider {
    String generateReply(List<ChatMessage> history, String userMessage);
}


