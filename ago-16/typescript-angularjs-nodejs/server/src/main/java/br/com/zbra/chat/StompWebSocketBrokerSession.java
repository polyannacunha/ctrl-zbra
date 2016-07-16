package br.com.zbra.chat;

public class StompWebSocketBrokerSession implements WebSocketBrokerSession {

    private final String sessionId;

    public StompWebSocketBrokerSession(String sessionId) {
        if (sessionId == null || "".equals(sessionId.trim())) {
            throw new IllegalArgumentException("Invalid session id");
        }
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}