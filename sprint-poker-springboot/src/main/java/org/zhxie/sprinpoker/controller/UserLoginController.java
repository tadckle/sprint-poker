package org.zhxie.sprinpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zhxie.sprinpoker.domain.User;
import org.zhxie.sprinpoker.service.UserService;

/**
 * Created by jianyang on 2019/1/8.
 */

@RestController
@RequestMapping("/poker/user")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/regist",method= RequestMethod.POST)
    public boolean regist(@RequestBody User user) {
        User findUser = userService.findByUserName(user.getUserName());
        if (findUser != null) {
            return false;
        }
        User savedUser = userService.save(user);
        return savedUser != null;
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public boolean login(@RequestBody User user) {
        User findUser = userService.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        return findUser != null;
    }

}
