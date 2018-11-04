package sprint;

public class ChatMessage {

	private int actionType;

	private String name;

	private String chatMessage;

	public ChatMessage(int actionType, String name, String chatMessage) {
		super();
		this.actionType = actionType;
		this.name = name;
		this.chatMessage = chatMessage;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}

}
