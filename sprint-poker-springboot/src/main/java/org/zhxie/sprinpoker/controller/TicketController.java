package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.service.TicketStoryService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/poker/ticketStoryPoint")
public class TicketController {

    @Autowired
    private TicketStoryService ticketStoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<TicketRecord> queryByDate(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageLimit) {
        if (Strings.isNullOrEmpty(date)) {
            return ticketStoryService.queryAll(pageNum, pageLimit);
        } else {
            LocalDate localDate = LocalDate.parse(date);
            return ticketStoryService.queryByDate(localDate, pageNum, pageLimit);
        }
    }

    @RequestMapping( method = RequestMethod.POST)
    public Boolean save(@RequestBody TicketRecord ticketRecord) {
        ticketRecord.setId(null);
        TicketRecord result = ticketStoryService.save(ticketRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Boolean update(@PathVariable int id, @RequestBody TicketRecord ticketRecord) {
        Optional<TicketRecord> optional = ticketStoryService.findById(id);
        if (!optional.isPresent()){
            return false;
        }
        ticketRecord.setId(id);
        TicketRecord result = ticketStoryService.save(ticketRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteById(@PathVariable int id) {
        ticketStoryService.deleteById(id);
        return true;
    }

}
