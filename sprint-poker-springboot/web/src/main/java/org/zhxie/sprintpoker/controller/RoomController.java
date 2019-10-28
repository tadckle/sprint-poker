package org.zhxie.sprintpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zhxie.sprintpoker.entity.Room;
import org.zhxie.sprintpoker.entity.dto.PageableDTO;
import org.zhxie.sprintpoker.entity.dto.ResponseResult;
import org.zhxie.sprintpoker.entity.game.SingleGameRecord;
import org.zhxie.sprintpoker.entity.dto.GameDTO;
import org.zhxie.sprintpoker.exception.CommandException;
import org.zhxie.sprintpoker.repository.SocketSessionRegistry;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

  @Autowired
  SocketSessionRegistry webAgentSessionRegistry;

  @MessageMapping("/rooms")
  @SendTo("/pocker/rooms")
  public List<Room> getRoomList(Principal user) {
    return webAgentSessionRegistry.getRooms();
  }

  @MessageMapping("/addRoom")
  @SendTo("/pocker/rooms")
  public List<Room> addRoom(Room room) {
    webAgentSessionRegistry.createRoom(room);
    return webAgentSessionRegistry.getRooms();
  }

  @ResponseBody
  @PostMapping("/api/rooms/{roomName}")
  public ResponseResult checkRoomPassword(@RequestBody Room room) {
    Optional<Room> findRoom = webAgentSessionRegistry.getRooms().stream().filter(roomItem -> (room.getName().equals(roomItem.getName()) && (room.getRoomPassword().equals(roomItem.getRoomPassword())))).findAny();
      if(findRoom.isPresent()) {
        return new ResponseResult(ResponseResult.SUCCESS, "房间密码正确");
      } else {
        return new ResponseResult(ResponseResult.FAIL, "房间密码不正确！");
      }
  }

  @MessageMapping("/joinPockerBoard/{roomName}/{curPage}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO joinPockerBoardByRoomId(Principal user, @DestinationVariable String roomName, @DestinationVariable Integer curPage) throws
          CommandException {
    //TODO: join the room and remember websocket session id
    System.out.println(user.getName());
    if (!webAgentSessionRegistry.isInRoom(roomName, user.getName())) {
      webAgentSessionRegistry.joinRoom(roomName, user.getName());
    }
    return webAgentSessionRegistry.getSingleGameRecord(roomName, curPage, user.getName());
  }

  @MessageMapping("/onClickPocker/{roomName}/{curPage}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onClickPocker(Principal user, SingleGameRecord.SingelPlayerScore singelPlayerScore, @DestinationVariable String
          roomName, @DestinationVariable Integer curPage) {
    System.out.println(user.getName());
    singelPlayerScore.setPlayerName(user.getName());
    webAgentSessionRegistry.updateRoomScoreByPlayer(singelPlayerScore, roomName, curPage);
    return webAgentSessionRegistry.getSingleGameRecord(roomName, curPage, user.getName());
  }

  @MessageMapping("/onNextGame/{roomName}/{curPage}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onNextGame(Principal user, @DestinationVariable String roomName, @DestinationVariable Integer curPage) {
    webAgentSessionRegistry.onNextGame(user.getName(), roomName, curPage);
    final GameDTO singleGameRecord = webAgentSessionRegistry.getSingleGameRecord(roomName, curPage, user.getName());
    singleGameRecord.setReset(true);
    return singleGameRecord;
  }

  @MessageMapping("/onAddStory/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onAddStory(Principal user, @RequestBody PageableDTO pagebleDTO) {
    webAgentSessionRegistry.onAddStory(pagebleDTO);
    return webAgentSessionRegistry.getSingleGameRecord(pagebleDTO.getRoomName(), pagebleDTO.getCurPage(), user.getName());
  }

  @MessageMapping("/onNavigateToPage/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onNavigateToPage(Principal user, @RequestBody PageableDTO pagebleDTO) {
    return webAgentSessionRegistry.getSingleGameRecord(pagebleDTO.getRoomName(), pagebleDTO.getCurPage(), user.getName());
  }
}
