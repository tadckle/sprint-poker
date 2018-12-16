package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.PokerInfo;
import org.zhxie.sprinpoker.service.PokerInfoService;

import java.time.LocalDate;

@RestController
@RequestMapping("/poker/showPokerInfo")
public class ShowPokerInfoController {

    @Autowired
    private PokerInfoService pokerInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<PokerInfo> queryByDate(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageLimit) {
        if (Strings.isNullOrEmpty(date)) {
            return pokerInfoService.queryAll(pageNum, pageLimit);
        } else {
            LocalDate localDate = LocalDate.parse(date);
            return pokerInfoService.queryByDate(localDate, pageNum, pageLimit);
        }
    }
}
