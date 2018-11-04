package sprint;

public class Room {

	private String roomNum;

	private String creator;

	public Room(String roomNum, String creator) {
		super();
		this.roomNum = roomNum;
		this.creator = creator;
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


}
