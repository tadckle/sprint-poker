package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketStoryPointRecord;
import org.zhxie.sprinpoker.service.TicketStoryPointRecordService;

import java.time.LocalDate;

@RestController
@RequestMapping("/poker/showTicketStoryPointInfo")
public class ShowTicketStoryPointInfoController {

    @Autowired
    private TicketStoryPointRecordService ticketStoryPointRecordService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<TicketStoryPointRecord> queryByDate(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageLimit) {
        if (Strings.isNullOrEmpty(date)) {
            return ticketStoryPointRecordService.queryAll(pageNum, pageLimit);
        } else {
            LocalDate localDate = LocalDate.parse(date);
            return ticketStoryPointRecordService.queryByDate(localDate, pageNum, pageLimit);
        }
    }
}
