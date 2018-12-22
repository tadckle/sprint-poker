package org.zhxie.sprinpoker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.service.TicketRecordService;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private TicketRecordService ticketRecordService;


    @Test
    public void testSave(){
        TicketRecord ticketRecord = new TicketRecord();
        ticketRecord.setTicketNum("DOM-123");
        ticketRecord.setDescription("DOM-123:description");
        ticketRecord.setStoryPoint(3.0);
        ticketRecord.setUsers("jianyang,jason,xianchen,da-long,yangliu");
        ticketRecord.setDate(LocalDate.now());
        ticketRecordService.save(ticketRecord);
    }

    @Test
    public void testQueryByDate() {
        LocalDate date = LocalDate.now();
        Page<TicketRecord> pokerInfos = ticketRecordService.queryByDate(date, 1, 50);
        Assert.assertEquals(1, pokerInfos.getTotalElements());
    }
}
