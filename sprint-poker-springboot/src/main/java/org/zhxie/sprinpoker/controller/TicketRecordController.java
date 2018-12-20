package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.service.TicketRecordService;

import java.time.LocalDate;

@RestController
@RequestMapping("/poker/showTicketStoryPointInfo")
public class TicketRecordController {

    @Autowired
    private TicketRecordService ticketRecordService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<TicketRecord> queryByDate(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageLimit) {
        if (Strings.isNullOrEmpty(date)) {
            return ticketRecordService.queryAll(pageNum, pageLimit);
        } else {
            LocalDate localDate = LocalDate.parse(date);
            return ticketRecordService.queryByDate(localDate, pageNum, pageLimit);
        }
    }
}
