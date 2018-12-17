package org.zhxie.sprinpoker;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhxie.sprinpoker.domain.TicketStoryPointRecord;
import org.zhxie.sprinpoker.service.TicketStoryPointRecordService;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private TicketStoryPointRecordService ticketStoryPointRecordService;


//    @Test
    public void testSave(){
        TicketStoryPointRecord ticketStoryPointRecord = new TicketStoryPointRecord();
        ticketStoryPointRecord.setTicketNum("DOM-123");
        ticketStoryPointRecord.setDescription("DOM-123:description");
        ticketStoryPointRecord.setStoryPoint(3.0);
        ticketStoryPointRecord.setUsers("jianyang,jason,xianchen,da-long,yangliu");
        ticketStoryPointRecord.setDate(LocalDate.now());
        ticketStoryPointRecordService.save(ticketStoryPointRecord);
    }

//    @Test
    public void testQueryByDate() {
        LocalDate date = LocalDate.now();
        Page<TicketStoryPointRecord> pokerInfos = ticketStoryPointRecordService.queryByDate(date, 0, 50);
        Assert.assertEquals(6, pokerInfos.getTotalElements());
    }
}
