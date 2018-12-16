package org.zhxie.sprinpoker.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.zhxie.sprinpoker.domain.PokerInfo;

public interface IPokerInfoDAO extends JpaRepository<PokerInfo, Integer> , JpaSpecificationExecutor<PokerInfo> {
}
