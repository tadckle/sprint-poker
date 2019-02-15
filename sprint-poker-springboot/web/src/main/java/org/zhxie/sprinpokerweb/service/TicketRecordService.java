package org.zhxie.sprinpokerweb.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zhxie.sprinpokerweb.domain.TicketRecord;
import org.zhxie.sprinpokerweb.repository.dao.ITicketRecordDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketRecordService {
    @Autowired
    private ITicketRecordDAO ticketRecordDAO;

    public Optional<TicketRecord> findById(int id) {
        return ticketRecordDAO.findById(id);
    }

    public void deleteById(int id) {
        ticketRecordDAO.deleteById(id);
    }

    public void deleteByIds(List<Integer> ids) {
        List<TicketRecord> ticketRecords = ids.stream().map(id -> new TicketRecord(id)).collect(Collectors.toList());
        ticketRecordDAO.deleteInBatch(ticketRecords);
    }

    public TicketRecord save(TicketRecord ticketRecord) {
        return ticketRecordDAO.save(ticketRecord);
    }

    public Page<TicketRecord> queryByDate(LocalDate date, int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return ticketRecordDAO.findByDate(date, pageable);
    }

    public Page<TicketRecord> queryByTicketNum(String ticketNum, int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return ticketRecordDAO.findByTicketNumIgnoreCase(ticketNum, pageable);
    }

    public Page<TicketRecord> queryByTicketNumAndDate(String ticketNum, LocalDate date, int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return ticketRecordDAO.findByTicketNumIgnoreCaseAndDate(ticketNum, date, pageable);
    }

    public Page<TicketRecord> queryAll(int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return ticketRecordDAO.findAll(pageable);
    }


}