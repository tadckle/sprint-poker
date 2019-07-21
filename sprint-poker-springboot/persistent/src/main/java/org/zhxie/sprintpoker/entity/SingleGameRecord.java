package org.zhxie.sprintpoker.entity;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;


@Data
public class SingleGameRecord {
    private String name;
    private String roomNum;
    private Map<String, SingelPlayerScore> player2Score = Maps.newHashMap();


    public void update(SingelPlayerScore singelPlayerScore) {
        if (!player2Score.containsKey(singelPlayerScore.getPlayerName())) {
            player2Score.put(singelPlayerScore.getPlayerName(), singelPlayerScore);
        } else {
            SingelPlayerScore store = player2Score.get(singelPlayerScore.getPlayerName());
            store.setFibonacciNum(singelPlayerScore.getFibonacciNum());
            store.setClicked(singelPlayerScore.isClicked());
        }
    }

    public void removeScoreRecord(String exitUserId) {
        player2Score.remove(exitUserId);
    }

    @Data
    public static class SingelPlayerScore {
        private String fibonacciNum = "??";
        private boolean clicked = false;
        private String playerName;
    }

    public SingelPlayerScore GetScore(String playerName) {
        if (!player2Score.containsKey(playerName)) {
            throw new UnsupportedOperationException("Cannot find the player");
        }
        return player2Score.get(playerName);
    }

    public void addScoreRecord(String palyerName) {
        player2Score.put(palyerName, new SingelPlayerScore());
    }

}
