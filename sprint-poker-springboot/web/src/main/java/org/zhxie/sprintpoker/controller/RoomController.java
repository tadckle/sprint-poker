package org.zhxie.sprintpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.zhxie.sprintpoker.entity.Room;
import org.zhxie.sprintpoker.entity.game.SingleGameRecord;
import org.zhxie.sprintpoker.entity.dto.GameDTO;
import org.zhxie.sprintpoker.exception.CommandException;
import org.zhxie.sprintpoker.repository.SocketSessionRegistry;

import java.security.Principal;
import java.util.List;

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

  @MessageMapping("/joinPockerBoard/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO joinPockerBoardByRoomId(Principal user, @DestinationVariable String roomName) throws
          CommandException {
    //TODO: join the room and remember websocket session id
    System.out.println(user.getName());
    if (!webAgentSessionRegistry.isInRoom(roomName, user.getName())) {
      webAgentSessionRegistry.joinRoom(roomName, user.getName());
    }
    return webAgentSessionRegistry.getSingleGameRecord(roomName);
  }

  @MessageMapping("/onClickPocker/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onClickPocker(Principal user, SingleGameRecord.SingelPlayerScore singelPlayerScore, @DestinationVariable String
          roomName) {
    System.out.println(user.getName());
    singelPlayerScore.setPlayerName(user.getName());
    webAgentSessionRegistry.updateRoomScoreByPlayer(singelPlayerScore, roomName);
    return webAgentSessionRegistry.getSingleGameRecord(roomName);
  }

  @MessageMapping("/onNextGame/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public GameDTO onNextGame(Principal user, @DestinationVariable String
          roomName) {
    webAgentSessionRegistry.onNextGame(user.getName(), roomName);
    final GameDTO singleGameRecord = webAgentSessionRegistry.getSingleGameRecord(roomName);
    singleGameRecord.setReset(true);
    return singleGameRecord;
  }

}
