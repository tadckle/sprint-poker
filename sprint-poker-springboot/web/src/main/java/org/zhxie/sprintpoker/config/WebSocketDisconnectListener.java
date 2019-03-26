package org.zhxie.sprintpoker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.zhxie.sprintpoker.entity.Room;
import org.zhxie.sprintpoker.repository.SocketSessionRegistry;

import java.util.List;

@Component
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private SocketSessionRegistry socketSessionRegistry;

  @Override
  public void onApplicationEvent(SessionDisconnectEvent event) {
    String webSocketConnectionID = event.getSessionId();
    // remove the user in room according to the web socket session ID
    final List<Room> rooms = socketSessionRegistry.removeUser(event.getUser().getName());
    for (Room room: rooms) {
      String roomName = room.getName();
      template.convertAndSend("/pocker/pockerBoard/"+ roomName, socketSessionRegistry.getPlayersByRoomID(roomName));
    }

  }
}
