package org.zhxie.sprintpoker.controller;

import com.google.common.collect.Maps;
import org.zhxie.sprintpoker.entity.User;
import org.zhxie.sprintpoker.entity.dto.ResponseResult;
import org.zhxie.sprintpoker.entity.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zhxie.sprintpoker.service.UserService;
import org.zhxie.sprintpoker.util.JwtUtil;

import java.util.Map;

/**
 * Created by jianyang on 2019/1/8.
 */

@RestController
public class UserLoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user/login",method= RequestMethod.POST)
    public ResponseResult login(@RequestBody User user) {
        User findUser = userService.findByUserNameAndPassword(user.getName(), user.getPassword());
        if (findUser == null) {
            return new ResponseResult(ResponseResult.LOGIN_ERROR, "用户名或密码错误");
        } else {
            //生成token
            String token = JwtUtil.createJWT(findUser.getId().toString(),
                    findUser.getName(), "user");
            Map<String, Object> data = Maps.newHashMap();
            data.put("token",token);
            return new ResponseResult(ResponseResult.SUCCESS, "登录成功", data);
        }
    }

    @RequestMapping(value="/user/regist",method= RequestMethod.POST)
    public ResponseResult regist(@RequestBody UserDTO user) {
        User findUser = userService.findByUserName(user.getUserName());
        if (findUser != null) {
            return new ResponseResult(ResponseResult.REGIST_ERROR, "用户名已经被注册");
        }
        userService.save(user);
        return new ResponseResult(ResponseResult.SUCCESS, "注册成功");

    }
}