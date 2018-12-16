package org.zhxie.sprinpoker.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.zhxie.sprinpoker.domain.PokerInfo;
import org.zhxie.sprinpoker.repository.dao.IPokerInfoDAO;
import java.time.LocalDate;

@Service
public class PokerInfoService {
    @Autowired
    private IPokerInfoDAO pokerInfoDAO;


    public void save(PokerInfo pokerInfo) {
        pokerInfoDAO.save(pokerInfo);
    }

    public Page<PokerInfo> queryByDate(LocalDate date, int pageNum, int pageLimit){
        PokerInfo pokerInfo = new PokerInfo();
        pokerInfo.setDate(date);
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id","storyPoint","users", "description");
        Example<PokerInfo> example = Example.of(pokerInfo, matcher);
        return pokerInfoDAO.findAll(example, pageable);
    }

    public Page<PokerInfo> queryAll(int pageNum, int pageLimit){
        Pageable pageable = PageRequest.of(pageNum -1, pageLimit);
        return pokerInfoDAO.findAll(pageable);
    }


}
