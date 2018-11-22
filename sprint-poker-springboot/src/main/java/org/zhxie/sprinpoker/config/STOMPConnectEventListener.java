package org.zhxie.sprinpoker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

import java.util.List;

public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        final List<String> login = sha.getNativeHeader("login");
        String agentId;
        if (login != null && !login.isEmpty()) {
            agentId = login.get(0);
        } else {
            agentId = "";
        }
        String sessionId = sha.getSessionId();
        webAgentSessionRegistry.registerSessionId(agentId,sessionId);
    }

}
