package com.pidima.chatbot.services.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pidima.chatbot.config.OpenAiProperties;
import com.pidima.chatbot.models.ChatMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(prefix = "openai", name = "api-key")
public class OpenAiProvider implements AiProvider {
    private static final Logger log = LoggerFactory.getLogger(OpenAiProvider.class);

    private final OpenAiProperties properties;
    private final RestTemplate restTemplate;

    public OpenAiProvider(OpenAiProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String generateReply(List<ChatMessage> history, String userMessage) {
        try {
            List<OpenAiMessage> messages = new ArrayList<>();
            for (ChatMessage m : history) {
                messages.add(new OpenAiMessage(m.getRole().name().toLowerCase(), m.getContent()));
            }
            messages.add(new OpenAiMessage("user", userMessage));

            OpenAiRequest request = new OpenAiRequest(properties.getModel(), messages);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(properties.getApiKey());

            HttpEntity<OpenAiRequest> entity = new HttpEntity<>(request, headers);
            OpenAiResponse response = restTemplate.postForObject(
                URI.create(properties.getBaseUrl() + "/chat/completions"), entity, OpenAiResponse.class);

            if (response != null && response.choices != null && !response.choices.isEmpty()) {
                return response.choices.get(0).message.content;
            }
        } catch (HttpClientErrorException e) {
            handleClientError(e);
        } catch (HttpServerErrorException e) {
            handleServerError(e);
        } catch (RestClientException e) {
            log.error("Network error calling OpenAI API: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error calling OpenAI API: {}", e.getMessage(), e);
        }
        return "I'm sorry, I couldn't generate a response at this time.";
    }

    private void handleClientError(HttpClientErrorException e) {
        HttpStatusCode status = e.getStatusCode();
        String responseBody = e.getResponseBodyAsString();
        
        if (status.value() == 401) {
            log.error("OpenAI API authentication failed. Please check your API key.");
        } else if (status.value() == 403) {
            log.error("OpenAI API access forbidden. Please check your API key permissions.");
        } else if (status.value() == 429) {
            log.warn("OpenAI API rate limit exceeded. Response: {}", responseBody);
        } else if (status.value() == 400) {
            log.error("Invalid request to OpenAI API: {}", responseBody);
        } else {
            log.error("OpenAI API client error {}: {}", status, responseBody);
        }
    }

    private void handleServerError(HttpServerErrorException e) {
        HttpStatusCode status = e.getStatusCode();
        String responseBody = e.getResponseBodyAsString();
        
        if (status.value() == 500) {
            log.error("OpenAI API internal server error: {}", responseBody);
        } else if (status.value() == 502) {
            log.error("OpenAI API bad gateway error: {}", responseBody);
        } else if (status.value() == 503) {
            log.error("OpenAI API service unavailable: {}", responseBody);
        } else {
            log.error("OpenAI API server error {}: {}", status, responseBody);
        }
    }

    static class OpenAiRequest {
        public String model;
        public List<OpenAiMessage> messages;
        public OpenAiRequest(String model, List<OpenAiMessage> messages) {
            this.model = model;
            this.messages = messages;
        }
    }

    static class OpenAiMessage {
        public String role;
        public String content;
        public OpenAiMessage(@JsonProperty("role") String role, @JsonProperty("content") String content) {
            this.role = role;
            this.content = content;
        }
    }

    static class OpenAiResponse {
        public List<Choice> choices;
        static class Choice {
            public Message message;
        }
        static class Message {
            public String role;
            public String content;
        }
    }
}


