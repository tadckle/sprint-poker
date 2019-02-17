package org.zhxie.sprinpokerweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zhxie.sprinpokerweb.domain.User;
import org.zhxie.sprinpokerweb.repository.dao.IUserDAO;

/**
 * Created by jianyang on 1/7/2019.
 */

@Service
public class UserService {
    @Autowired
    private IUserDAO userDAO;
    private BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    public User save(User user) {
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userDAO.save(user);
    }

    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        User user = userDAO.findByUserName(userName);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

}
