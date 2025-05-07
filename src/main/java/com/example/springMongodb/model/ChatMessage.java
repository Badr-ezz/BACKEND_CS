package com.example.springMongodb.model;




public class ChatMessage {
    private Long teamId;
    private String content;
    private String sender;
    private long timestamp;

    // Private constructor for builder
    private ChatMessage(Builder builder) {
        this.teamId = builder.teamId;
        this.content = builder.content;
        this.sender = builder.sender;
        this.timestamp = builder.timestamp;
    }

    // Getters
    public Long getTeamId() {
        return teamId;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters (if needed)
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Builder static method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static final class Builder {
        private Long teamId;
        private String content;
        private String sender;
        private long timestamp;

        private Builder() {
        }

        public Builder teamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(this);
        }
    }

    // No-args constructor
    public ChatMessage() {
    }

    // All-args constructor
    public ChatMessage(Long teamId, String content, String sender, long timestamp) {
        this.teamId = teamId;
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
    }
}