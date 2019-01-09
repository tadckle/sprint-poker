package org.zhxie.sprinpoker.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.domain.User;

/**
 * Created by jianyang on 1/7/2019.
 */

public interface IUserDAO extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

}
