package org.zhxie.sprinpoker.repository;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;
import org.zhxie.sprinpoker.domain.Player;
import org.zhxie.sprinpoker.domain.Room;
import org.zhxie.sprinpoker.exception.CommandException;

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
  private static final List<Room> rooms = Lists.newArrayList();
  private static final Map<String, List<Player>> roomId2PlayersMap = Maps.newHashMap();

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
    return rooms;
  }

  public List<Player> getPlayersByRoomID(String roomID) {
    return roomId2PlayersMap.getOrDefault(roomID, Lists.newArrayList());
  }

  public void joinRoom(String roomID, String userId) {
    Player player = new Player(userId);
    if (roomId2PlayersMap.containsKey(roomID)) {
      roomId2PlayersMap.get(roomID).add(player);
    } else {
      roomId2PlayersMap.put(roomID, Lists.newArrayList(player));
    }
  }

  public String getUserIdBySessionId(String sessionID) throws CommandException {
    String userId = "";
    for(Map.Entry<String, Set<String>>user2SessionID : getAllSessionIds().entrySet()) {
      if (user2SessionID.getValue().contains(sessionID)) {
        userId = user2SessionID.getKey();
      }
    }
    if(!Strings.isNullOrEmpty(userId)) {
      return userId;
    }
    throw new CommandException();
  }

  public boolean isInRoom(String roomName,String userId) {
    return roomId2PlayersMap.get(roomName).contains(userId);
  }

  public void addRoom(Room room) {
    room.setRoomNum(String.valueOf(getRooms().size()));
    rooms.add(room);
    roomId2PlayersMap.put(room.getName(), Lists.newArrayList());
  }

  public void updateRoomScoreByPlayer(Player player, String roomName) {
    List<Player> players = roomId2PlayersMap.getOrDefault(roomName, Lists.newArrayList());
    for (Player p: players) {
      if (p.getName().equals(player.getName())) {
        p.setFibonacciNum(player.getFibonacciNum());
      }
    }
  }
}
