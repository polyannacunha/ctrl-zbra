package br.com.zbra.chat.stomp;

class StompWebSocketBrokerSession implements WebSocketBrokerSession {

    private final String sessionId;

    StompWebSocketBrokerSession(String sessionId) {
        if (sessionId == null || "".equals(sessionId.trim())) {
            throw new IllegalArgumentException("Invalid session id");
        }
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}