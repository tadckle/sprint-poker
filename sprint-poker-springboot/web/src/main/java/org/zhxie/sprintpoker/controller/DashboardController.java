package org.zhxie.sprintpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zhxie.sprintpoker.entity.TicketRecord;
import org.zhxie.sprintpoker.entity.dto.ResponseResult;
import org.zhxie.sprintpoker.service.TicketRecordService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class DashboardController {

  @Autowired
  private TicketRecordService ticketRecordService;
  @Autowired
  private HttpServletRequest request;

  @GetMapping("/api/dashboard")
  public List<TicketRecord> getDashboardRecords(Integer pageOffset, Integer limit) {
    return ticketRecordService.findAll(pageOffset, limit);
  }

  @PostMapping("/api/dashboard")

  public ResponseResult save(Principal user, @RequestBody  TicketRecord ticketRecord ) {
    ticketRecord.setCreator(user.getName());
    ticketRecordService.save(ticketRecord);
    return new ResponseResult(ResponseResult.SUCCESS, "保存成功！");
  }

//  @RequestMapping(method = RequestMethod.POST)
//  public ResponseResult save2(TicketRecord ticketRecord) {
//    ticketRecordService.save(ticketRecord);
//    return new ResponseResult(ResponseResult.SUCCESS, "保存成功！");
//  }

//  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//  public ResponseResult update(@PathVariable int id, @RequestBody TicketRecord ticketRecord) {
//    Optional<TicketRecord> optional = ticketRecordService.findById(id);
//    if (!optional.isPresent()) {
//      return new ResponseResult(ResponseResult.FAIL, "记录不存在");
//    }
////    ticketRecord.setId(id);
////    ticketRecord.setDate(optional.get().getDate());
//    ticketRecordService.save(ticketRecord);
//    return new ResponseResult(ResponseResult.SUCCESS, "修改成功！");
//  }

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
