package org.zhxie.sprintpoker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.zhxie.sprintpoker.repository.SocketSessionRegistry;

/**
 * Created by zhxie on 11/16/2018.
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure message broker options.
     * <p>
     * config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // carry the message back to the client on destinations prefixed with "/poker"
        config.enableSimpleBroker("/pocker");

        // designate the "/app" prefix for messages bound for @MessageMapping
        config.setApplicationDestinationPrefixes("/app");

    }

    /**
     * Register STOMP endpoints mapping each to a specific URL and (optionally)
     * enabling and configuring SockJS fallback options.
     *
     * @param config
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry config) {
        config.addEndpoint("/pocker-websocket").setAllowedOrigins("*")
//                .addInterceptors(httpSessionIdHandshakeInterceptor())
                .withSockJS();
    }

//    @Bean
//    public HandshakeInterceptor httpSessionIdHandshakeInterceptor() {
//        return new HttpHandshakeInterceptor();
//    }

    @Bean
    public SocketSessionRegistry SocketSessionRegistry() {
        return new SocketSessionRegistry();
    }


}
