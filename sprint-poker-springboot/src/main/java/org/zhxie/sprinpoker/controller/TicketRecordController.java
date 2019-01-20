package org.zhxie.sprinpoker.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.zhxie.sprinpoker.domain.TicketRecord;
import org.zhxie.sprinpoker.domain.dto.ResponseResult;
import org.zhxie.sprinpoker.service.TicketRecordService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/poker/ticketRecord")
public class TicketRecordController {

  @Autowired
  private TicketRecordService ticketRecordService;
  @Autowired
  private HttpServletRequest request;

  @RequestMapping(method = RequestMethod.GET)
  public Page<TicketRecord> queryByDate(@RequestParam(defaultValue = "") String ticketNum, @RequestParam
          (defaultValue = "") String date, @RequestParam(defaultValue = "1") int pageNum, @RequestParam
                                                (defaultValue = "50") int pageLimit) {
    if (!Strings.isNullOrEmpty(ticketNum) && !Strings.isNullOrEmpty(date)) {
      LocalDate localDate = LocalDate.parse(date);
      return ticketRecordService.queryByTicketNumAndDate(ticketNum, localDate, pageNum, pageLimit);
    }
    if (!Strings.isNullOrEmpty(ticketNum) && Strings.isNullOrEmpty(date)) {
      return ticketRecordService.queryByTicketNum(ticketNum, pageNum, pageLimit);
    }
    if (Strings.isNullOrEmpty(ticketNum) && !Strings.isNullOrEmpty(date)) {
      LocalDate localDate = LocalDate.parse(date);
      return ticketRecordService.queryByDate(localDate, pageNum, pageLimit);
    }
    return ticketRecordService.queryAll(pageNum, pageLimit);
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

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseResult deleteById(@PathVariable int id) {
    ticketRecordService.deleteById(id);
    return new ResponseResult(ResponseResult.SUCCESS, "删除成功！");
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseResult deleteByIds(@RequestBody List<Integer> ids) {
    ticketRecordService.deleteByIds(ids);
    return new ResponseResult(ResponseResult.SUCCESS, "删除成功！");
  }

}
