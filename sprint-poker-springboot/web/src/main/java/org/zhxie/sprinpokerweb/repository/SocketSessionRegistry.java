package org.zhxie.sprinpokerweb.repository;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;
import org.zhxie.sprinpokerweb.domain.Player;
import org.zhxie.sprinpokerweb.domain.Room;
import org.zhxie.sprinpokerweb.exception.CommandException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SocketSessionRegistry {

  //this map save every session
  //这个集合存储session
  private final ConcurrentMap<String, Set<String>> userSessionIds = new ConcurrentHashMap();
  private final Object lock = new Object();
  private static final Map<String, Room> roomId2Room = Maps.newHashMap();

  public SocketSessionRegistry() {
  }

  /**
   * 获取所有session
   *
   * @return
   */
  public ConcurrentMap<String, Set<String>> getAllSessionIds() {
    return this.userSessionIds;
  }

  /**
   * register session
   *
   * @param user
   * @param sessionId
   */
  public void registerSessionId(String user, String sessionId) {
    Assert.notNull(user, "User must not be null");
    Assert.notNull(sessionId, "Session ID must not be null");
    Object var3 = this.lock;
    synchronized (this.lock) {
      Object set = (Set) this.userSessionIds.get(user);
      if (set == null) {
        set = new CopyOnWriteArraySet();
        this.userSessionIds.put(user, (Set<String>) set);
      }

      ((Set) set).add(sessionId);
    }
  }

  public void unregisterSessionId(String userName, String sessionId) {
    Assert.notNull(userName, "User Name must not be null");
    Assert.notNull(sessionId, "Session ID must not be null");
    Object var3 = this.lock;
    synchronized (this.lock) {
      Set set = (Set) this.userSessionIds.get(userName);
      if (set != null && set.remove(sessionId) && set.isEmpty()) {
        this.userSessionIds.remove(userName);
      }

    }
  }

  public List<Room> getRooms() {
    return Lists.newArrayList(roomId2Room.values());
  }

  public List<Player> getPlayersByRoomID(String roomID) {
    return roomId2Room.get(roomID).getPlayer();
  }

  public void joinRoom(String roomID, String userId) {
    Player player = new Player(userId);

    if (roomId2Room.containsKey(roomID)) {
      roomId2Room.get(roomID).add(player);
    } else {
      Room room = new Room();
      room.add(player);
      roomId2Room.put(roomID, room);
    }
  }

  public String getUserIdBySessionId(String sessionID) throws CommandException {
    String userId = "";
    for (Map.Entry<String, Set<String>> user2SessionID : getAllSessionIds().entrySet()) {
      if (user2SessionID.getValue().contains(sessionID)) {
        userId = user2SessionID.getKey();
      }
    }
    if (!Strings.isNullOrEmpty(userId)) {
      return userId;
    }
    throw new CommandException();
  }

  public boolean isInRoom(String roomName, String userId) {
    return roomId2Room.get(roomName).hasPlayer(userId);
  }

  public void createRoom(Room room) {
    room.setRoomNum(String.valueOf(roomId2Room.size()));
    roomId2Room.put(room.getName(), room);
  }

  public void updateRoomScoreByPlayer(Player player, String roomName) {
    List<Player> players = roomId2Room.get(roomName).getPlayer();
    for (Player p : players) {
      if (p.getName().equals(player.getName())) {
        p.setFibonacciNum(player.getFibonacciNum());
      }
    }
  }

  public boolean hasUserLogined(String userId) {
    return userSessionIds.containsKey(userId) && !userSessionIds.get(userId).isEmpty();
  }

  public void removeSession(String sessionId) {
    String exitUserId = "";
    synchronized (this.lock) {
      for (Map.Entry<String, Set<String>> user2SessionId : userSessionIds.entrySet()) {
        Set<String> sessionIdValues = user2SessionId.getValue();
        sessionIdValues.remove(sessionId);
        if (sessionIdValues.isEmpty()) {
          exitUserId = user2SessionId.getKey();
          break;
        }
      }
      userSessionIds.remove(exitUserId);
      Room emtpyRoom = null;
      for (Room room : roomId2Room.values()) {
        room.removePlayer(exitUserId);
        if (room.isEmpty()) {
          emtpyRoom = room;
          break;
        }
      }
      if (emtpyRoom != null) {
        roomId2Room.remove(emtpyRoom);
      }
    }
  }
}
