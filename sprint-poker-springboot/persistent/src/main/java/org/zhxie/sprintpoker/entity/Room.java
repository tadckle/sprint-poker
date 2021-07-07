package org.zhxie.sprintpoker.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.zhxie.sprintpoker.entity.dto.CandidateDTO;
import org.zhxie.sprintpoker.entity.dto.FinalResultDTO;
import org.zhxie.sprintpoker.entity.dto.PageableDTO;
import org.zhxie.sprintpoker.entity.game.RoomGameRecord;
import org.zhxie.sprintpoker.entity.game.SingleGameRecord;

import java.util.List;
import java.util.Set;

@Data
public class Room {

    private String roomNum;

    private String roomPassword;

    private String owner;

    private String name;

    private String desc;

    private final Set<Player> players = Sets.newHashSet();

    private RoomGameRecord gameRecord = new RoomGameRecord();

    public Room() {

    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
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
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public void add(Player player) {
        players.add(player);
        gameRecord.addScoreRecord(player.getName(), name);
    }

    public void removePlayer(String exitUserId) {
        players.removeIf(p -> p.getName().equals(exitUserId));
        gameRecord.removePlayer(exitUserId);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public void nextGame(int curPage) {
        gameRecord.resetPage(curPage);
        for (Player p : players) {
            gameRecord.getCurPage(curPage).addScoreRecord(p.getName(), name);
        }
    }

    public SingleGameRecord getCurPageSingleGRecord(int curPage) {
        return  gameRecord.getCurPage(curPage);
    }

    public void addStory(PageableDTO dto) {
        gameRecord.addStory(players, name, dto);
    }

    public void updateFinalScore(CandidateDTO candidateDTO, String ownerName) {
        if (ownerName.equals(owner)) {
            SingleGameRecord record = getCurPageSingleGRecord(candidateDTO.getPageNum());
            record.setFinalGameRecord(candidateDTO.getScore());
        }
    }

  public List<FinalResultDTO> getFinalScores() {
      return gameRecord.getFinalScores();
  }
}
