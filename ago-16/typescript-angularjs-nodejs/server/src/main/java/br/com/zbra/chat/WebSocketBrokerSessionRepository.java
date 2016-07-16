package br.com.zbra.chat;

public interface WebSocketBrokerSessionRepository<T extends WebSocketBrokerSession> {

    T findBySessionId(String sessionId);

    T createNewSession(String sessionId);

    boolean isAuthenticated(String sessionId);

    void addSession(T session);

    void removeSessionById(String sessionId);
}