package org.zhxie.sprintpoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.zhxie.sprintpoker.entity.Player;
import org.zhxie.sprintpoker.entity.Room;
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
  public List<Player> joinPockerBoardByRoomId(Principal user, @DestinationVariable String roomName) throws
          CommandException {
    //TODO: join the room and remember websocket session id
    if (!webAgentSessionRegistry.isInRoom(roomName, user.getName())) {
      webAgentSessionRegistry.joinRoom(roomName, user.getName());
    }
    return webAgentSessionRegistry.getPlayersByRoomID(roomName);
  }

  @MessageMapping("/onClickPocker/{roomName}")
  @SendTo("/pocker/pockerBoard/{roomName}")
  public List<Player> onClickPocker(Principal user, Player player, @DestinationVariable String
          roomName) {
    player.setName(user.getName());
    webAgentSessionRegistry.updateRoomScoreByPlayer(player, roomName);
    return webAgentSessionRegistry.getPlayersByRoomID(roomName);
  }

}
