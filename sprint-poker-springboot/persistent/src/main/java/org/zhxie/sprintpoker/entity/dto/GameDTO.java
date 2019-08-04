package org.zhxie.sprintpoker.entity.dto;

import lombok.Data;
import org.zhxie.sprintpoker.entity.SingleGameRecord;

import java.util.List;

@Data
public class GameDTO {
    private List<SingleGameRecord.SingelPlayerScore> playerScoreList;
    private boolean shown;
    private String roomName;
}
