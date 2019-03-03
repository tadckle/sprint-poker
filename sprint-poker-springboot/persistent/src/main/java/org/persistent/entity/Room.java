package org.persistent.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class Room {

	private String roomNum;

	private String creator;

	private String name;

	private String desc;

	private final Set<Player> players = Sets.newHashSet();

	public Room() {

  }

	public Room(String roomNum, String creator) {
		super();
		this.roomNum = roomNum;
		this.creator = creator;
	}

  public Room(String roomNum, String creator, String name, String desc) {
    this(roomNum, creator);
	  this.name = name;
	  this.desc = desc;
  }

  public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<Player> getPlayer() {
    return Lists.newArrayList(players);
  }

  public boolean hasPlayer(String playerName) {
	  for (Player p: players) {
	    if (p.getName().equals(playerName)) {
	      return true;
      }
    }
	  return false;
  }

  public void add(Player player) {
	  players.add(player);
  }

  public void removePlayer(String exitUserId) {
    players.removeIf(p -> p.getName().equals(exitUserId));
  }

  public boolean isEmpty() {
	  return players.isEmpty();
  }
}
