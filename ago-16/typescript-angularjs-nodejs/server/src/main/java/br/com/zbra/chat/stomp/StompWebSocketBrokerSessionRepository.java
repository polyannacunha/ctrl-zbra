package br.com.zbra.chat.stomp;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StompWebSocketBrokerSessionRepository implements WebSocketBrokerSessionRepository<StompWebSocketBrokerSession> {

    private final Map<String, StompWebSocketBrokerSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public StompWebSocketBrokerSession findBySessionId(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public StompWebSocketBrokerSession createNewSession(String sessionId) {
        final StompWebSocketBrokerSession newSession = new StompWebSocketBrokerSession(sessionId);
        sessionMap.put(sessionId, newSession);
        describeSessions();
        return newSession;
    }

    @Override
    public boolean isAuthenticated(String sessionId) {
        return true;
    }

    @Override
    public void addSession(StompWebSocketBrokerSession session) {
        sessionMap.put(session.getSessionId(), session);
        describeSessions();
    }

    @Override
    public void removeSessionById(String sessionId) {
        sessionMap.remove(sessionId);
        describeSessions();
    }

    private void describeSessions() {
        System.out.println(String.format("Available sessions [count=%d]...", sessionMap.size()));
        for (StompWebSocketBrokerSession session : sessionMap.values()) {
            System.out.println(String.format("Session [id=%s]", session.getSessionId()));
        }
    }

}
