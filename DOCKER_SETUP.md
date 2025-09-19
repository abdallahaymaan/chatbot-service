# Docker Setup for Chatbot Service

This document explains how to run the chatbot service with PostgreSQL using Docker Compose.

## Prerequisites

- Docker and Docker Compose installed
- OpenAI API key (optional, but required for full functionality)

## Quick Start

1. **Set up environment variables** (optional):
   ```bash
   # Copy the example environment file
   cp env.example .env
   
   # Edit .env and add your OpenAI API key
   # OPENAI_API_KEY=your_actual_api_key_here
   ```

2. **Start the services**:
   ```bash
   docker-compose up -d
   ```

3. **Check the logs**:
   ```bash
   # View all logs
   docker-compose logs -f
   
   # View backend logs only
   docker-compose logs -f backend
   
   # View database logs only
   docker-compose logs -f postgres
   ```

4. **Access the application**:
   - Backend API: http://localhost:8080
   - Health check: http://localhost:8080/actuator/health
   - PostgreSQL: localhost:5432

## Services

### PostgreSQL Database
- **Image**: postgres:15-alpine
- **Port**: 5432
- **Database**: chatbot_db
- **Username**: chatbot_user
- **Password**: chatbot_password
- **Data persistence**: Uses Docker volume `postgres_data`

### Backend Service
- **Build**: Uses local Dockerfile
- **Port**: 8080
- **Environment**: docker profile
- **Dependencies**: Waits for PostgreSQL to be healthy

## Database Schema

The database is automatically initialized with:
- `chat_sessions` table for storing chat sessions
- `chat_messages` table for storing individual messages
- Proper indexes for performance
- Sample data (optional)

## Environment Variables

You can override default settings by creating a `.env` file:

```bash
# OpenAI API Configuration
OPENAI_API_KEY=your_openai_api_key_here

# Database Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/chatbot_db
SPRING_DATASOURCE_USERNAME=chatbot_user
SPRING_DATASOURCE_PASSWORD=chatbot_password

# Application Configuration
SPRING_PROFILES_ACTIVE=docker
```

## Useful Commands

```bash
# Start services in background
docker-compose up -d

# Stop services
docker-compose down

# Stop and remove volumes (WARNING: deletes all data)
docker-compose down -v

# Rebuild and start services
docker-compose up --build -d

# View service status
docker-compose ps

# Execute commands in running containers
docker-compose exec backend sh
docker-compose exec postgres psql -U chatbot_user -d chatbot_db

# View database logs
docker-compose logs postgres

# View application logs
docker-compose logs backend
```

## Development

For development, you can:

1. **Run with live reload** (if using Spring Boot DevTools):
   ```bash
   # Mount source code for hot reload
   docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
   ```

2. **Connect to database from host**:
   ```bash
   psql -h localhost -p 5432 -U chatbot_user -d chatbot_db
   ```

3. **Access application logs**:
   ```bash
   docker-compose logs -f backend
   ```

## Troubleshooting

### Backend won't start
- Check if PostgreSQL is healthy: `docker-compose ps`
- Check backend logs: `docker-compose logs backend`
- Ensure all environment variables are set correctly

### Database connection issues
- Verify PostgreSQL is running: `docker-compose logs postgres`
- Check network connectivity: `docker-compose exec backend ping postgres`
- Verify database credentials in application logs

### Port conflicts
- Change ports in `docker-compose.yml` if 8080 or 5432 are already in use
- Update environment variables accordingly

## Data Persistence

- Database data is persisted in Docker volume `postgres_data`
- To reset the database: `docker-compose down -v && docker-compose up -d`
- To backup data: `docker-compose exec postgres pg_dump -U chatbot_user chatbot_db > backup.sql`
