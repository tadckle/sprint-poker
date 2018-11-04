package sprint;

import java.util.List;

public class ReturnMsg {

	private int actionType;

	private String message;

	private List<Room> rooms;

	public ReturnMsg(int actionType, String message) {
		super();
		this.actionType = actionType;
		this.message = message;
	}

	public ReturnMsg(int actionType, List<Room> roomList) {
		super();
		this.actionType = actionType;
		this.rooms = roomList;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}
