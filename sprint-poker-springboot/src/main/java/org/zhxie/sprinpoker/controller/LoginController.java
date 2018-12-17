package org.zhxie.sprinpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

/**
 * Created by zhxie on 11/16/2018.
 */

@Controller
public class LoginController {

  @Autowired
  private SocketSessionRegistry sessionRepo;

  @MessageMapping("/login")
  @SendTo("/pocker/login")
  public State login(String userId, SimpMessageHeaderAccessor headerAccessor) {
    if (userId == null || userId.equals("")) {
      return new State(false, userId);
    }
    final String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
    System.out.println(sessionId);
    System.out.println(String.format("%s is logining", userId));
    sessionRepo.registerSessionId(userId, sessionId);
    return new State(true, userId);
  }

  public static class State {
    private boolean hasLogin;
    private String userName;

    public boolean isHasLogin() {
      return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
      this.hasLogin = hasLogin;
    }

    public State(boolean hasLogin, String userName) {
      this.hasLogin = hasLogin;
      this.userName = userName;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }
  }
}
