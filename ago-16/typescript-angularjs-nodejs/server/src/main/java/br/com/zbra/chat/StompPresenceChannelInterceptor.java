package br.com.zbra.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class StompPresenceChannelInterceptor extends ChannelInterceptorAdapter {
    private final WebSocketBrokerSessionRepository webSocketBrokerSessionRepository;

    @Autowired
    public StompPresenceChannelInterceptor(WebSocketBrokerSessionRepository webSocketBrokerSessionRepository) {
        this.webSocketBrokerSessionRepository = webSocketBrokerSessionRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        final StompCommand stompCommand = sha.getCommand();
        if (stompCommand == null) {
            return message;
        }

        final String sessionId = sha.getSessionId();
        switch (stompCommand) {
            case CONNECT:
                webSocketBrokerSessionRepository.createNewSession(sessionId);
                break;
            case CONNECTED:
                break;
            case DISCONNECT:
                webSocketBrokerSessionRepository.removeSessionById(sessionId);
                break;
            default:
                break;
        }

        return message;
    }
}
