package org.zhxie.sprinpoker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created by zhxie on 11/16/2018.
 */

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure message broker options.
     *
     config     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // carry the message back to the client on destinations prefixed with "/poker"
        config.enableSimpleBroker("/answer");

        // designate the "/poker" prefix for messages bound for @MessageMapping
        config.setApplicationDestinationPrefixes("/poker");
    }

    /**
     * Register STOMP endpoints mapping each to a specific URL and (optionally)
     * enabling and configuring SockJS fallback options.
     *
     * @param config
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry config) {
        config.addEndpoint("/poker-websocket").withSockJS();
    }
}
