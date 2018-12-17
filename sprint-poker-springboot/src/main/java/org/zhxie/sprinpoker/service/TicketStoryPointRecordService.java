package org.zhxie.sprinpoker.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.zhxie.sprinpoker.domain.TicketStoryPointRecord;
import org.zhxie.sprinpoker.repository.dao.ITicketStoryPointRecordDAO;
import java.time.LocalDate;

@Service
public class TicketStoryPointRecordService {
    @Autowired
    private ITicketStoryPointRecordDAO storyPointRecordDAO;


    public void save(TicketStoryPointRecord ticketStoryPointRecord) {
        storyPointRecordDAO.save(ticketStoryPointRecord);
    }

    public Page<TicketStoryPointRecord> queryByDate(LocalDate date, int pageNum, int pageLimit){
        TicketStoryPointRecord ticketStoryPointRecord = new TicketStoryPointRecord();
        ticketStoryPointRecord.setDate(date);
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id","storyPoint","users", "description");
        Example<TicketStoryPointRecord> example = Example.of(ticketStoryPointRecord, matcher);
        return storyPointRecordDAO.findAll(example, pageable);
    }

    public Page<TicketStoryPointRecord> queryAll(int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return storyPointRecordDAO.findAll(pageable);
    }


}
