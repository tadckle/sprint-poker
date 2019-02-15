package org.zhxie.sprinpokerweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zhxie.sprinpokerweb.repository.SocketSessionRegistry;

/**
 * Created by zhxie on 11/16/2018.
 */

@Controller
public class LoginController {

  @Autowired
  private SocketSessionRegistry sessionRepo;

//  @MessageMapping("/login")
//  @SendTo("/pocker/login")
//  public State login(String userId, SimpMessageHeaderAccessor headerAccessor) {
//    if (userId == null || userId.equals("")) {
//      return new State(false, userId);
//    }
//
//    if (sessionRepo.hasUserLogined(userId)) {
//      // the used has been logined, can't use this name again.
//      return new State(false, userId);
//    }
//    final String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
//    sessionRepo.registerSessionId(userId, sessionId);
//    return new State(true, userId);
//  }

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

  @RequestMapping("/login")
  public String login(){
    return "login";
  }
}
