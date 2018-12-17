package org.zhxie.sprinpoker.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.zhxie.sprinpoker.domain.TicketStoryPointRecord;

public interface ITicketStoryPointRecordDAO extends JpaRepository<TicketStoryPointRecord, Integer> , JpaSpecificationExecutor<TicketStoryPointRecord> {
}
