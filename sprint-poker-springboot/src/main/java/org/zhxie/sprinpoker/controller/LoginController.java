package org.zhxie.sprinpoker.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.zhxie.sprinpoker.domain.Player;

/**
 * Created by zhxie on 11/16/2018.
 */

@Controller
public class LoginController {

    @MessageMapping("/login")
    @SendTo("/pocker/login")
    public State login(String player) {
        if (player == null || player.equals("")) {
            return new State(false);
        }
        System.out.println(String.format("%s is logining", player));
        return new State(true);
    }

    public static class State {
        private boolean  hasLogin;

        public boolean isHasLogin() {
            return hasLogin;
        }

        public void setHasLogin(boolean hasLogin) {
            this.hasLogin = hasLogin;
        }

        public State(boolean hasLogin) {
            this.hasLogin = hasLogin;
        }
    }
}
