package org.zhxie.sprinpoker.controller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zhxie.sprinpoker.domain.User;
import org.zhxie.sprinpoker.service.UserService;
import org.zhxie.sprinpoker.util.JwtUtil;

import java.util.Map;

/**
 * Created by jianyang on 2019/1/8.
 */

@RestController
@RequestMapping("/poker/user")
public class UserLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value="/regist",method= RequestMethod.POST)
    public ResponseResult regist(@RequestBody User user) {
        User findUser = userService.findByUserName(user.getUserName());
        if (findUser != null) {
            return new ResponseResult(false, "用户名已经被注册");
        }
        User savedUser = userService.save(user);
        //生成token
        String token = jwtUtil.createJWT(savedUser.getId().toString(),
                savedUser.getUserName(), "user");
        Map<String, Object> data = Maps.newHashMap();
        data.put("token",token);
        return new ResponseResult(true, "注册成功", data);

    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public ResponseResult login(@RequestBody User user) {
        User findUser = userService.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        if (findUser == null) {
            return new ResponseResult(false, "用户名或密码错误");
        } else {
            //生成token
            String token = jwtUtil.createJWT(findUser.getId().toString(),
                    findUser.getUserName(), "user");
            Map<String, Object> data = Maps.newHashMap();
            data.put("token",token);
            return new ResponseResult(true, "登录成功", data);
        }
    }

}
