package org.zhxie.sprinpoker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.zhxie.sprinpoker.domain.Room;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @MessageMapping("/rooms"  )
    @SendTo("/pocker/rooms")
    public List<Room> getRoomList(SimpMessageHeaderAccessor headerAccessor) {
      System.out.println(headerAccessor.getSessionAttributes().get("sessionId").toString());
      return webAgentSessionRegistry.getRooms();
    }

    @MessageMapping("/addRoom")
    @SendTo("/pocker/rooms")
    public List<Room> addRoom(Room room) {
      room.setRoomNum(String.valueOf(webAgentSessionRegistry.getRooms().size()));
      webAgentSessionRegistry.getRooms().add(room);
      return webAgentSessionRegistry.getRooms();
    }

}
