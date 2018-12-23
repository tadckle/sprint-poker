package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.service.TicketRecordService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/poker/ticketRecord")
public class TicketRecordController {

    @Autowired
    private TicketRecordService ticketRecordService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<TicketRecord> queryByDate(@RequestParam(defaultValue = "") String ticketNum, @RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "50") int pageLimit) {
        if (!Strings.isNullOrEmpty(ticketNum) && !Strings.isNullOrEmpty(date)) {
            LocalDate localDate = LocalDate.parse(date);
            return ticketRecordService.queryByTicketNumAndDate(ticketNum, localDate,pageNum,pageLimit);
        }
        if (!Strings.isNullOrEmpty(ticketNum) && Strings.isNullOrEmpty(date)) {
            return ticketRecordService.queryByTicketNum(ticketNum,pageNum,pageLimit);
        }
        if (Strings.isNullOrEmpty(ticketNum) && !Strings.isNullOrEmpty(date)) {
            LocalDate localDate = LocalDate.parse(date);
            return ticketRecordService.queryByDate(localDate,pageNum,pageLimit);
        }
        return ticketRecordService.queryAll(pageNum, pageLimit);
    }

    @RequestMapping( method = RequestMethod.POST)
    public Boolean save(@RequestBody TicketRecord ticketRecord) {
        ticketRecord.setId(null);
        ticketRecord.setDate(LocalDate.now());
        TicketRecord result = ticketRecordService.save(ticketRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Boolean update(@PathVariable int id, @RequestBody TicketRecord ticketRecord) {
        Optional<TicketRecord> optional = ticketRecordService.findById(id);
        if (!optional.isPresent()){
            return false;
        }
        ticketRecord.setId(id);
        ticketRecord.setDate(optional.get().getDate());
        TicketRecord result = ticketRecordService.save(ticketRecord);
        return result != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteById(@PathVariable int id) {
        ticketRecordService.deleteById(id);
        return true;
    }

}
