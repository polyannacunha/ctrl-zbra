package br.com.zbra.chat.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class StompServerConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
    private static final String APPLICATION_CONTEXT_NAME = "/zbra-chat";
    private static final String WEB_SOCKET_STOMP_ENDPOINT_NAME = "/stomp";
    private static final String COMMAND_TOPIC_CONTEXT_NAME = "/topic";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(WEB_SOCKET_STOMP_ENDPOINT_NAME)
                .setAllowedOrigins("*")
                .withSockJS()
                .setStreamBytesLimit(512 * 1024)
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(24 * 60 * 60 * 1000)
                .setHeartbeatTime(500);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(APPLICATION_CONTEXT_NAME);
        config.enableSimpleBroker(COMMAND_TOPIC_CONTEXT_NAME);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(8);
    }
}
