package org.zhxie.sprinpoker.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.repository.dao.ITicketStoryPointRecordDAO;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TicketRecordService {
    @Autowired
    private ITicketStoryPointRecordDAO storyPointRecordDAO;

    public Optional<TicketRecord> findById(int id) {
        return storyPointRecordDAO.findById(id);
    }

    public void deleteById(int id) {
        storyPointRecordDAO.deleteById(id);
    }

    public TicketRecord save(TicketRecord ticketRecord) {
        return storyPointRecordDAO.save(ticketRecord);
    }

    public Page<TicketRecord> queryByDate(LocalDate date, int pageNum, int pageLimit){
        TicketRecord ticketRecord = new TicketRecord();
        ticketRecord.setDate(date);
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id","storyPoint","users", "description");
        Example<TicketRecord> example = Example.of(ticketRecord, matcher);
        return storyPointRecordDAO.findAll(example, pageable);
    }

    public Page<TicketRecord> queryAll(int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return storyPointRecordDAO.findAll(pageable);
    }


}
