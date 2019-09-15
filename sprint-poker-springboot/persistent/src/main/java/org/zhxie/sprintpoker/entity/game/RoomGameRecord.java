package org.zhxie.sprintpoker.entity.game;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class RoomGameRecord {

  private final List<SingleGameRecord> recordList = Lists.newArrayList();
  private final String roomName;

  public int getTotalGameRecord() {
    return recordList.size();
  }

  public void addSingleGameRecord() {
    recordList.add(new SingleGameRecord());
  }

  public void getSelectdGameRecord(int selectedGameIndex) {
    if (selectedGameIndex <0 || selectedGameIndex >= recordList.size()) {
      throw new UnsupportedOperationException("Error Index");
    }
    recordList.get(selectedGameIndex);
  }
}
