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
    public Boolean login(String player) {
        System.out.println(String.format("%s is logining", player));
        return true;
    }

}
