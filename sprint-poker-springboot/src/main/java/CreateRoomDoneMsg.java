package sprint;

public class CreateRoomDoneMsg {
	private int actionType;

	private String roomId;

	public CreateRoomDoneMsg(int actionType, String roomId) {
		super();
		this.actionType = actionType;
		this.roomId = roomId;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
