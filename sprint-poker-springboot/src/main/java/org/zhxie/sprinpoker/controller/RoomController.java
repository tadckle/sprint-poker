package org.zhxie.sprinpoker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.zhxie.sprinpoker.domain.Room;
import org.zhxie.sprinpoker.repository.SocketSessionRegistry;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @MessageMapping("/rooms")
    @SendTo("/pocker/rooms")
    public List<Room> getRoomList() {
      System.out.println("get room list invoke");
      Room room = new Room("1", "zhxie", "Hello room", "the first room");
      boolean add = webAgentSessionRegistry.getRooms().add(room);
      return webAgentSessionRegistry.getRooms();
    }
}
