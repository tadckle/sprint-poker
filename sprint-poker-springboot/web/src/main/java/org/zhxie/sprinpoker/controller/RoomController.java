package org.zhxie.sprinpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.zhxie.sprinpoker.domain.Player;
import org.zhxie.sprinpoker.domain.Room;
import org.zhxie.sprinpoker.exception.CommandException;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

import java.util.List;

@Controller
public class RoomController {

  @Autowired
  SocketSessionRegistry webAgentSessionRegistry;

  @MessageMapping("/rooms")
  @SendTo("/pocker/rooms")
  public List<Room> getRoomList() {
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
  public List<Player> joinPockerBoardByRoomId(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String
          roomName) throws
          CommandException {
    final String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
    String userId = webAgentSessionRegistry.getUserIdBySessionId(sessionId);
    if (!webAgentSessionRegistry.isInRoom(roomName, userId)) {
      webAgentSessionRegistry.joinRoom(roomName, userId);
    }
    return webAgentSessionRegistry.getPlayersByRoomID(roomName);
  }

  @MessageMapping("/onClickPocker/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public List<Player> onClickPocker(Player Player, @DestinationVariable String
          roomName) {
    webAgentSessionRegistry.updateRoomScoreByPlayer(Player, roomName);
    return webAgentSessionRegistry.getPlayersByRoomID(roomName);
  }

}
