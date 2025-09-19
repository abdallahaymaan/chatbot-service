-- Initialize the chatbot database
-- This script runs when the PostgreSQL container starts for the first time

-- Create extensions if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the chat_sessions table
CREATE TABLE IF NOT EXISTS chat_sessions (
    session_id VARCHAR(255) PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Create the chat_messages table
CREATE TABLE IF NOT EXISTS chat_messages (
    id BIGSERIAL PRIMARY KEY,
    session_id VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ASSISTANT', 'SYSTEM')),
    content TEXT NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (session_id) REFERENCES chat_sessions(session_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_chat_messages_session_id ON chat_messages(session_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_timestamp ON chat_messages(timestamp);
CREATE INDEX IF NOT EXISTS idx_chat_sessions_created_at ON chat_sessions(created_at);

-- Insert some sample data (optional)
-- You can remove this section if you don't want sample data
INSERT INTO chat_sessions (session_id, created_at) 
VALUES ('sample-session-1', NOW())
ON CONFLICT (session_id) DO NOTHING;

INSERT INTO chat_messages (session_id, role, content, timestamp)
VALUES 
    ('sample-session-1', 'USER', 'Hello, how can you help me?', NOW() - INTERVAL '5 minutes'),
    ('sample-session-1', 'ASSISTANT', 'I can help you with various tasks. What would you like to know?', NOW() - INTERVAL '4 minutes')
ON CONFLICT DO NOTHING;
