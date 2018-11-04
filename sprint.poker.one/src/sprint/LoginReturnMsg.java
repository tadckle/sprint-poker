package sprint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginReturnMsg {
	private static final DecimalFormat FORMAT = new DecimalFormat("#.0");

	private int actionType;

	private List<Player> players;

	private String averagePoint;

	private List<PointStatistics> statistics = new ArrayList<>();

	public LoginReturnMsg(int actionType, List<Player> players) {
		super();
		this.actionType = actionType;
		this.players = players;
		boolean notAllSelected = players.stream().filter(player -> player.getFibonacciNum() == -1).findAny().isPresent();
		if (notAllSelected) {
			averagePoint = "XXX";
		} else {
			double averageDoublePoint = players.stream().mapToInt(player -> player.getFibonacciNum())
					.filter(val -> val != Command.UNKNOW_FIBONICA).average().orElse(0);
			averagePoint = String.valueOf(FORMAT.format(averageDoublePoint));

			Map<Integer, Long> collect = players.stream().collect(
					Collectors.groupingBy(Player::getFibonacciNum, Collectors.counting()));
			collect.forEach((key, value) -> statistics.add(new PointStatistics(key, value)));
		}
	}

	public List<PointStatistics> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<PointStatistics> statistics) {
		this.statistics = statistics;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getAveragePoint() {
		return averagePoint;
	}

	public void setAveragePoint(String averagePoint) {
		this.averagePoint = averagePoint;
	}

}
