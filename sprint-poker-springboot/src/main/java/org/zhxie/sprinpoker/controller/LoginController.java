package org.zhxie.sprinpoker.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by zhxie on 11/16/2018.
 */

@Controller
public class LoginController {

    @MessageMapping("/login")
    @SendTo("/answer/login")
    public boolean login() {
        return true;
    }

}
