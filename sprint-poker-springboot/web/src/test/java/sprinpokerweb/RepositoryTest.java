package org.zhxie.sprinpokerweb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhxie.sprinpokerweb.domain.TicketRecord;
import org.zhxie.sprinpokerweb.service.TicketRecordService;

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
        Assert.assertEquals(2, pokerInfos.getTotalElements());
    }

    @Test
    public void testQueryByTicketNum() {
        Page<TicketRecord> pokerInfos = ticketRecordService.queryByTicketNum("doM-123", 1, 50);
        Assert.assertEquals(3, pokerInfos.getTotalElements());
    }

    @Test
    public void testQueryByTicketNumAndDate() {
        Page<TicketRecord> pokerInfos = ticketRecordService.queryByTicketNumAndDate("doM-123", LocalDate.now(),1,50);
        Assert.assertEquals(2, pokerInfos.getTotalElements());
    }

}