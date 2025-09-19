package com.pidima.chatbot.config;

import com.pidima.chatbot.services.ai.AiProvider;
import com.pidima.chatbot.services.ai.OpenAiProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    @ConditionalOnMissingBean(AiProvider.class)
    public AiProvider fallbackAiProvider() {
        return (history, userMessage) -> "(demo) Echo: " + userMessage;
    }
}


