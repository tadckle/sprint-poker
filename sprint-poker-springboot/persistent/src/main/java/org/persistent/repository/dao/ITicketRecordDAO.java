package org.persistent.repository.dao;

import org.persistent.entity.TicketRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface ITicketRecordDAO extends JpaRepository<TicketRecord, Integer> , JpaSpecificationExecutor<TicketRecord> {

    Page<TicketRecord> findByTicketNumIgnoreCase(String tickerNum, Pageable pageable);

    Page<TicketRecord> findByDate(LocalDate date, Pageable pageable);

    Page<TicketRecord> findByTicketNumIgnoreCaseAndDate(String tickerNum, LocalDate date, Pageable pageable);

}
