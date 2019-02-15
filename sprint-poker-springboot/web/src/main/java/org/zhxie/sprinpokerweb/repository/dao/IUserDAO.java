package org.zhxie.sprinpokerweb.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhxie.sprinpokerweb.domain.User;

/**
 * Created by jianyang on 1/7/2019.
 */

public interface IUserDAO extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

}
