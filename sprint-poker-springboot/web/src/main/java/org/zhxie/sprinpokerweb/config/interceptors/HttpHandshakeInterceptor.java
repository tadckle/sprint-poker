package org.zhxie.sprinpokerweb.config.interceptors;

import org.persistent.repository.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

  @Autowired
  SocketSessionRegistry socketSessionRegistry;

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                 Map<String, Object> attributes) throws Exception {
    if (request instanceof ServletServerHttpRequest) {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
      HttpSession session = servletRequest.getServletRequest().getSession();
      attributes.put("sessionId", session.getId());
    }
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                             Exception exception) {
    if (request instanceof ServletServerHttpRequest) {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
      HttpSession session = servletRequest.getServletRequest().getSession();
      socketSessionRegistry.removeSession(session.getId());
    }
  }


}
