package org.zhxie.sprinpoker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

@Component
public class WebSockerDisconnectListenner implements ApplicationListener<SessionDisconnectEvent> {

  @Autowired
  private SocketSessionRegistry socketSessionRegistry;

  @Override
  public void onApplicationEvent(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    final String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
    socketSessionRegistry.removeSession(sessionId);
    System.out.println(String.format("connection disconnect {}",sessionId));
  }
}
