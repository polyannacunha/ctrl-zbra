package br.com.zbra.chat.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class CommandChannelController {

    private final WebSocketBrokerSessionRepository webSocketBrokerSessionRepository;

    @Autowired
    public CommandChannelController(
            WebSocketBrokerSessionRepository webSocketBrokerSessionRepository) {
        this.webSocketBrokerSessionRepository = webSocketBrokerSessionRepository;
    }

    private WebSocketBrokerSession checkSessionMessage(Message<Object> message) {
        final String sessionId = message.getHeaders().get(SimpMessageHeaderAccessor.SESSION_ID_HEADER, String.class);
        final WebSocketBrokerSession webSocketBrokerSession = webSocketBrokerSessionRepository.findBySessionId(sessionId);
        if (webSocketBrokerSession == null) {
            throw new IllegalStateException(String.format(
                    "Message could not be processed because session [id=%s] does not exists into repository. Try to authenticate.", sessionId));
        }
        System.out.println(String.format("=====> Message received from session [id=%s]", sessionId));
        return webSocketBrokerSession;
    }

    @MessageMapping("/topic")
    @SendTo("/topic")
    public String onTopicCommand(String message) throws Exception {
        return message;
    }
}