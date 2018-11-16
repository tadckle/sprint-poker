

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.zhxie.sprinpoker.domain.Player;
import org.zhxie.sprinpoker.domain.Room;

@ServerEndpoint("/poker")
public class PokerWebSocket {
	private static final Random random = new Random();

	private static final Gson GSON = new GsonBuilder().create();

	// Used to know whether a user name is occupied. Value is user name.
	private static final Map<Session, String> SESSION_USER_MAP = new HashMap<>();

	// Key is room id.
	private static final Map<String, List<Player>> ROOM_MAP = new HashMap<>();

	// Key room id, value is creator name;
	private static final List<Room> ROOMS = new ArrayList<>();

	private static Set<PokerWebSocket> pokerWebSockets = new HashSet<>();

	private String roomNumIamIn = "-1";

	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		pokerWebSockets.add(this);
	}

	@OnClose
	public void onClose() {
		String playerName = SESSION_USER_MAP.get(session);

		pokerWebSockets.remove(this);
		SESSION_USER_MAP.remove(session);

		updateAvailableRoomsAndPokerPlayers(playerName);
	}

	private Optional<Session> getSessionByUserName(String userName) {
		for (Entry<Session, String> entry : SESSION_USER_MAP.entrySet()) {
			if (entry.getValue().equals(userName)) {
				return Optional.of(entry.getKey());
			}
		}
		return Optional.empty();
	}

	private void updateAvailableRoomsAndPokerPlayers(String playerName) {
		List<Player> players = ROOM_MAP.get(roomNumIamIn);
		if (players == null) {
			return;
		}
		players.removeIf(aPlayer -> aPlayer.getName().equals(playerName));
		// Remove a room if there's no player.
		if (players.isEmpty()) {
			ROOM_MAP.remove(roomNumIamIn);
			ROOMS.removeIf(room -> room.getRoomNum().equals(roomNumIamIn));
		} else {
			getSessionByUserName(players.get(0).getName()).ifPresent(aSession -> {
				try {
					setAsHost(aSession);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		try {
			// Update room list.
			updateAllUserRoomList();
			if (ROOM_MAP.containsKey(roomNumIamIn)) {
				// If room still exists, which means there is still player. Update poker player list.
				sendAllUser(session, roomNumIamIn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setAsHost(Session session) throws IOException {
		sendMessage(session, new ReturnMsg(sprint.Command.SET_AS_HOST, ""));
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// Do nothing.
		System.out.println(error.getMessage());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		synchronized(PokerWebSocket.class) {
			sprint.Command command = GSON.fromJson(message, Command.class);
			Player player = command.getPlayer();
			if (Command.LOGIN == command.getType()) {
				if (command.getPlayer().getName() == null || command.getPlayer().getName().isEmpty()) {
					sendMessage(session, new ReturnMsg(Command.ERROR, "Please input a valid user name."));
					return;
				}
				boolean present = SESSION_USER_MAP.values().stream().filter(userName -> userName.equals(player.getName())).findAny().isPresent();
				if (present) {
					sendMessage(session, new ReturnMsg(Command.ERROR, "User name \"" + player.getName() + "\" already exists."));
				} else {
					SESSION_USER_MAP.put(session, command.getPlayer().getName());
					sendMessage(session, new LoginReturnMsg(Command.SHOW_LOGIN, new ArrayList<>()));
					updateRoomListItself();
				}
			} else if (Command.CREATE_ROOM == command.getType()) {
				try {
					String roomNum = String.valueOf(generateRoomNum());
					ROOM_MAP.put(roomNum, new ArrayList<>());
					ROOMS.add(new Room(roomNum, command.getPlayer().getName()));
					ROOM_MAP.get(roomNum).add(command.getPlayer());
					roomNumIamIn = roomNum;
					sendMessage(session, new CreateRoomDoneMsg(Command.CREATE_ROOM_DONE, roomNum));
					updateAllUserRoomList();
					sendAllUser(session, roomNum);
				} catch (Exception e) {
					sendMessage(session, new ReturnMsg(Command.ERROR, "Cannot create room!!!"));
				}
			} else if (Command.JOIN_ROOM == command.getType()) {
				if (ROOM_MAP.containsKey(command.getRoomNum())) {
					ROOM_MAP.get(command.getRoomNum()).add(command.getPlayer());
					roomNumIamIn = command.getRoomNum();
					sendMessage(session, new ReturnMsg(Command.JOIN_ROOM_DONE, command.getRoomNum()));
					sendAllUser(session, command.getRoomNum());
				} else {
					sendMessage(session, new ReturnMsg(Command.ERROR, "The room number \"" + command.getRoomNum() + "\" doesn't exist."));
				}
			} else if (Command.UPDATE_FIBONACI == command.getType()) {
				command.getPlayer();
				updateUser(command.getRoomNum(), command.getPlayer());
				sendAllUser(session, command.getRoomNum());
			} else if (Command.CLEAR == command.getType()) {
				clearAllUserFibonaciNum(command.getRoomNum());
			} else if (Command.CHAT == command.getType()) {
				broadcastChatMsg(command.getPlayer().getName(), command.getChatMsg());
			} else if (Command.RETURN_ROOM_DASHBOARD == command.getType()) {
				updateAvailableRoomsAndPokerPlayers(command.getPlayer().getName());
			}
		}

	}

	private void broadcastChatMsg(String name, String chatMsg) {
		pokerWebSockets.forEach(pokerWeb -> {
			try {
				sendMessage(pokerWeb.session, new ChatMessage(Command.CHAT, name, chatMsg));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void clearAllUserFibonaciNum(String roomNum) throws IOException {
		List<Player> players = ROOM_MAP.getOrDefault(roomNum, new ArrayList<>());
		players.forEach(aPlayer -> aPlayer.setFibonacciNum(-1));
		sendAllUser(session, roomNum);

		pokerWebSockets.forEach(pokerWeb -> {
			try {
				sendMessage(pokerWeb.session, new ReturnMsg(Command.CLEAR, ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void updateRoomListItself() throws IOException {
		// Update room list only itself.
		sendMessage(session, new ReturnMsg(Command.UPDATE_ROOMS, ROOMS));
	}

	private void updateAllUserRoomList() {
		pokerWebSockets.forEach(pokerWeb -> {
			try {
				sendMessage(pokerWeb.session, new ReturnMsg(Command.UPDATE_ROOMS, ROOMS));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void updateUser(String roommNum, Player newPalyer) {
		List<Player> players = ROOM_MAP.getOrDefault(roommNum, new ArrayList<>());
		Optional<Player> currentPlayer = players.stream().filter(curPlayer -> curPlayer.getName().equals(newPalyer.getName())).findAny();
		if (currentPlayer.isPresent()) {
			currentPlayer.get().setFibonacciNum(newPalyer.getFibonacciNum());
		}
	}

	private void sendAllUser(Session aSession, String roomNum) throws IOException {
		// Send to message to all connection that have same room number.
		pokerWebSockets.stream().filter(pokerWeb -> pokerWeb.roomNumIamIn.equals(roomNum)).forEach(pokerWeb -> {
			try {
				sendMessage(pokerWeb.session, new LoginReturnMsg(Command.ALL_USER, ROOM_MAP.getOrDefault(roomNum, new ArrayList<>())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void sendMessage(Session aSession, Object obj) throws IOException {
		aSession.getBasicRemote().sendText(GSON.toJson(obj));
	}

	// Throw exception when cannot allocate room number.
	public static final int generateRoomNum() throws Exception {
		int roomNum = nextNumber();
		int count = 0;
		while (ROOM_MAP.containsKey(roomNum)) {
			roomNum = nextNumber();
			count++;
			if (count > 200) {
				throw new Exception("All room is occupied.");
			}
		}
		return roomNum;
	}

	private static int nextNumber() {
		return random.nextInt(8999) + 1000;
	}

}