package org.zhxie.sprinpoker;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhxie.sprinpoker.domain.PokerInfo;
import org.zhxie.sprinpoker.service.PokerInfoService;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private PokerInfoService pokerInfoService;


//    @Test
    public void testSave(){
        PokerInfo pokerInfo = new PokerInfo();
        pokerInfo.setDescription("DOM-123");
        pokerInfo.setStoryPoint(3);
        pokerInfo.setUsers("jianyang,jason,xianchen,da-long,yangliu");
        pokerInfo.setDate(LocalDate.now());
        pokerInfoService.save(pokerInfo);
    }

//    @Test
    public void testQueryByDate() {
        LocalDate date = LocalDate.now();
        Page<PokerInfo> pokerInfos = pokerInfoService.queryByDate(date, 0, 50);
        Assert.assertEquals(6, pokerInfos.getTotalElements());
    }
}
