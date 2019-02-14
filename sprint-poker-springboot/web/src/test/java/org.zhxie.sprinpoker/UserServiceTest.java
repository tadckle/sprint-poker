package org.zhxie.sprinpoker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhxie.sprinpoker.domain.User;
import org.zhxie.sprinpoker.service.UserService;

/**
 * Created by jianyang on 2019/1/7.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void testSave() {
        User user = new User();
        user.setUserName("jianyang");
        user.setPassword("123");
        user.setEmail("jian.yang@asml.com");
        if (userService.findByUserName("jianyang") == null) {
            User savedUser = userService.save(user);
            Assert.assertEquals(user.getUserName(), savedUser.getUserName());
        }
    }

    @Test
    public void testFindByUserName() {
        User user = userService.findByUserName("jianyang");
        Assert.assertTrue(encoder.matches("123",user.getPassword()));
    }

    @Test
    public void testFindByUserNameAndPassword() {
        User user = userService.findByUserNameAndPassword("jianyang", "123");
        Assert.assertTrue(encoder.matches("123", user.getPassword()));
    }
}
