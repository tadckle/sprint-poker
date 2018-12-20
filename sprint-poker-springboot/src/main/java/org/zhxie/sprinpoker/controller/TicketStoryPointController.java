package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketStoryPointRecord;
import org.zhxie.sprinpoker.service.TicketStoryPointRecordService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/poker/ticketStoryPoint")
public class TicketStoryPointController {

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

    @RequestMapping( method = RequestMethod.POST)
    public Boolean save(@RequestBody TicketStoryPointRecord ticketStoryPointRecord) {
        ticketStoryPointRecord.setId(null);
        TicketStoryPointRecord result = ticketStoryPointRecordService.save(ticketStoryPointRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Boolean update(@PathVariable int id, @RequestBody TicketStoryPointRecord ticketStoryPointRecord) {
        Optional<TicketStoryPointRecord> optional = ticketStoryPointRecordService.findById(id);
        if (!optional.isPresent()){
            return false;
        }
        ticketStoryPointRecord.setId(id);
        TicketStoryPointRecord result = ticketStoryPointRecordService.save(ticketStoryPointRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteById(@PathVariable int id) {
        ticketStoryPointRecordService.deleteById(id);
        return true;
    }

}
