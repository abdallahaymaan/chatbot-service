# Pidima Chatbot Service

Java Spring Boot microservice for an AI-powered chat assistant with PostgreSQL persistence.

## Features

- **REST API**: Create sessions, send messages, get conversation history, health checks
- **AI Integration**: OpenAI GPT integration with fallback demo mode
- **Database**: PostgreSQL with Flyway migrations for schema management
- **Docker**: Full containerized setup with Docker Compose
- **Validation**: Request validation and global error handling
- **Testing**: Unit tests with MockMvc and integration tests
- **Monitoring**: Health checks and structured logging

## API Endpoints

- `POST /chat/session` — Create a new chat session
- `POST /chat/message` — Send a message and get AI response
- `GET /chat/history/{sessionId}` — Get conversation history
- `GET /health` — Health check endpoint

## Quick Start with Docker Compose

### Prerequisites
- Docker and Docker Compose installed
- OpenAI API key (optional, but required for full AI functionality)

### 1. Set up environment (optional)
```bash
# Copy environment template
cp env.example .env

# Edit .env and add your OpenAI API key
# OPENAI_API_KEY=your_actual_api_key_here
```

### 2. Start the services
```bash
# Start PostgreSQL and backend
docker-compose up -d

# Check logs
docker-compose logs -f
```

### 3. Access the application
- **API**: http://localhost:8080
- **Health Check**: http://localhost:8080/health
- **Database**: localhost:5432 (PostgreSQL)

## Development Setup

### Local Development (without Docker)
```bash
# Build the application
mvn clean package

# Run with local database
java -jar target/chatbot-service-0.0.1-SNAPSHOT.jar
```

### With Docker
```bash
# Build and run
docker-compose up --build -d

# View logs
docker-compose logs -f backend
```

## Database Configuration

The application uses PostgreSQL with:
- **Database**: `chatbot_db`
- **User**: `chatbot_user`
- **Password**: `chatbot_password`
- **Port**: `5432`

Schema is managed by Flyway migrations in `src/main/resources/db/migration/`.

## Configuration

### Environment Variables
- `OPENAI_API_KEY` — OpenAI API key (optional, uses demo mode if not set)
- `SPRING_DATASOURCE_URL` — Database connection URL
- `SPRING_PROFILES_ACTIVE` — Active Spring profile (default: `docker`)

### Application Profiles
- **Default**: Uses in-memory H2 database
- **Docker**: Uses PostgreSQL with Docker Compose
- **Production**: Configure for your production database

## Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ChatControllerTest

# Run with Docker
docker-compose exec backend mvn test
```

### Test Coverage
- Unit tests for controllers, services, and repositories
- Integration tests with MockMvc
- Database tests with test containers

## Docker Services

### PostgreSQL Database
- **Image**: postgres:16-alpine
- **Data Persistence**: Docker volume `postgres_data`
- **Health Checks**: Automatic health monitoring
- **Network**: Isolated `chatbot-network`

### Backend Service
- **Build**: Multi-stage Docker build
- **Dependencies**: Waits for PostgreSQL to be healthy
- **Restart Policy**: `unless-stopped`
- **Logging**: Structured JSON logging

## Useful Commands

```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# Rebuild and start
docker-compose up --build -d

# View service status
docker-compose ps

# Access database
docker-compose exec postgres psql -U chatbot_user -d chatbot_db

# View logs
docker-compose logs -f backend
docker-compose logs -f postgres

# Reset database (WARNING: deletes all data)
docker-compose down -v
```

## Architecture

### Components
- **Controllers**: REST API endpoints with validation
- **Services**: Business logic and AI integration
- **Repositories**: Data access layer with JPA
- **Models**: JPA entities for chat sessions and messages
- **Configuration**: Spring Boot auto-configuration

### AI Integration
- **OpenAI Provider**: Full GPT integration when API key is provided
- **Fallback Provider**: Demo echo mode when OpenAI is unavailable
- **Error Handling**: Graceful degradation with detailed logging

## Production Notes

- **Database**: Configure production PostgreSQL connection
- **Security**: Set up proper authentication and authorization
- **Monitoring**: Add application metrics and monitoring
- **Scaling**: Consider horizontal scaling with load balancers
- **Backup**: Set up database backup strategies


