## Pidima Chatbot Service

Java Spring Boot microservice template for an AI-powered documentation assistant.

### Features
- REST endpoints: create session, send message, get history, health check
- Validation, global error handling, basic logging
- OpenAI provider abstraction (pluggable)
- In-memory storage (swap for database later)
- Unit tests with MockMvc
- Docker containerization

### Endpoints
- POST `/chat/session` — create a new chat session
- POST `/chat/message` — send a message and get AI response
- GET `/chat/history/{session_id}` — get conversation history
- GET `/health` — health check

### Configuration
Environment variables:
- `OPENAI_API_KEY` — API key (optional for tests/dev)
Application settings in `src/main/resources/application.yml` under `openai`.

### Build & Run
```bash
mvn clean package
java -jar target/chatbot-service-0.0.1-SNAPSHOT.jar
```

Docker:
```bash
docker build -t pidima/chatbot-service:0.0.1 .
docker run -p 8080:8080 -e OPENAI_API_KEY=sk-... pidima/chatbot-service:0.0.1
```

### Notes
- This template uses an in-memory repository for simplicity. Replace with a persistent store for production (e.g., Postgres, Redis).
- The OpenAI client is a thin wrapper using Spring `RestTemplate`; swap for an async WebClient if needed.


