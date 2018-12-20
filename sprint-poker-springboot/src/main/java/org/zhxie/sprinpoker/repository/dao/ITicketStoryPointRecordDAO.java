package org.zhxie.sprinpoker.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.zhxie.sprinpoker.domain.TicketRecord;

public interface ITicketStoryPointRecordDAO extends JpaRepository<TicketRecord, Integer> , JpaSpecificationExecutor<TicketRecord> {
}
