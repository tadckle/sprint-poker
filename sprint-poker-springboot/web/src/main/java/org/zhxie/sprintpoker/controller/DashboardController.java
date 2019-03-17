package org.zhxie.sprintpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprintpoker.entity.TicketRecord;
import org.zhxie.sprintpoker.entity.dto.ResponseResult;
import org.zhxie.sprintpoker.service.TicketRecordService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

  @Autowired
  private TicketRecordService ticketRecordService;
  @Autowired
  private HttpServletRequest request;

  @RequestMapping(method = RequestMethod.GET)
  public List<TicketRecord> getDashboardRecords(Integer pageOffset, Integer limit) {
    return ticketRecordService.findAll(pageOffset, limit);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseResult save(@RequestBody  TicketRecord ticketRecord) {
//    ticketRecord.setId(null);
//    ticketRecord.setDate(LocalDate.now());
//    ticketRecordService.save(ticketRecord);
    return new ResponseResult(ResponseResult.SUCCESS, "保存成功！");
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseResult update(@PathVariable int id, @RequestBody TicketRecord ticketRecord) {
    Optional<TicketRecord> optional = ticketRecordService.findById(id);
    if (!optional.isPresent()) {
      return new ResponseResult(ResponseResult.FAIL, "记录不存在");
    }
//    ticketRecord.setId(id);
//    ticketRecord.setDate(optional.get().getDate());
    ticketRecordService.save(ticketRecord);
    return new ResponseResult(ResponseResult.SUCCESS, "修改成功！");
  }

//  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//  public ResponseResult deleteById(@PathVariable int id) {
//    ticketRecordService.deleteById(id);
//    return new ResponseResult(ResponseResult.SUCCESS, "删除成功！");
//  }
//
//  @RequestMapping(method = RequestMethod.DELETE)
//  public ResponseResult deleteByIds(@RequestBody List<Integer> ids) {
//    ticketRecordService.deleteByIds(ids);
//    return new ResponseResult(ResponseResult.SUCCESS, "删除成功！");
//  }

}
