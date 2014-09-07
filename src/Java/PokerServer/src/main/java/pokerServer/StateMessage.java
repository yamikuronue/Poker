package pokerServer;

import java.util.ArrayList;
import java.util.HashMap;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “StateMessage” represent a message going back to a client. 
 * This can represent a Game State or Lobby State message.
 * @author bgreen
 *
 */
public class StateMessage extends Message {

	StateType type;
	
	/**
	 * Constructor to create a message for a client.
	 * @param type The type of state message to create
	 * @param clientInvolved The client that the message is from or to.
	 */
	public StateMessage(StateType type, Client clientInvolved) {
		super(clientInvolved);
		this.type = type;
	}

	
	@Override
	public boolean isValid() {
		//Must have type
		if (type == null) return false;
		
		switch(type) {
			case LOBBY:
				return lobbyMessageIsValid();
			case GAME:
				return gameMessageIsValid();
			default:
				return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean lobbyMessageIsValid() {
		if (!parameters.containsKey("Games")) return false; //Must list games
		
		if (!(parameters.get("Games") instanceof ArrayList<?>)) return false;
		ArrayList<Object> gamesList = (ArrayList<Object>) parameters.get("Games");
		
		for(Object game : gamesList) {
			if (!(game instanceof HashMap<?, ?>)) return false; //Must be json object
			HashMap<String, Object> jsonGame = (HashMap<String, Object>) game;
			
			if (!(jsonGame.containsKey("ID"))) return false;
			if (!(jsonGame.containsKey("Open"))) return false;
			if (!(jsonGame.get("Open") instanceof Boolean)) return false;
			
			if (!(jsonGame.containsKey("Players"))) return false;
			if (!(jsonGame.get("Players") instanceof ArrayList<?>)) return false;
			ArrayList<HashMap<String, Object>> playersList = (ArrayList<HashMap<String, Object>>) jsonGame.get("Players");
			
			for (HashMap<String, Object> player : playersList) {
				if (!isValidPlayer(player, true)) return false;
			}
			
			//Watchers is optional
			if (jsonGame.containsKey("Watchers")) {
				if (!(jsonGame.get("Watchers") instanceof ArrayList<?>)) return false;
				ArrayList<HashMap<String, Object>> watchersList = (ArrayList<HashMap<String, Object>>) jsonGame.get("Watchers");

				for (HashMap<String, Object> watcher : watchersList) {
					if (!isValidPlayer(watcher, false)) return false;
				}
			}
			
			//check for extra params
			for (String key : jsonGame.keySet()) {
				if (!key.equalsIgnoreCase("ID")
						&& !key.equalsIgnoreCase("Open") 
						&& !key.equalsIgnoreCase("Players")
						&& !key.equalsIgnoreCase("Watchers"))
					return false;
			}
		}
		
		//Lobby Occupants
		if (!parameters.containsKey("LobbyOccupants")) return false; //Must list games
		
		if (!(parameters.get("LobbyOccupants") instanceof ArrayList<?>)) return false;
		ArrayList<HashMap<String, Object>> lobbyOccupants = (ArrayList<HashMap<String, Object>>) parameters.get("LobbyOccupants");
		
		for (HashMap<String, Object> occupant : lobbyOccupants) {
			if (!isValidPlayer(occupant, true)) return false;
		}
		
		return true;
	}
	
	private boolean gameMessageIsValid() {
		//Required fields
		if (!parameters.containsKey("Pot")) return false;
		if (!parameters.containsKey("Dealer")) return false;
		if (!parameters.containsKey("Actor")) return false;
		if (!parameters.containsKey("TableCards")) return false;
		if (!parameters.containsKey("You")) return false;
		if (!parameters.containsKey("OtherPlayers")) return false;
		if (!parameters.containsKey("LastAction")) return false;
		
		//Validate table cards
		if (!(parameters.get("TableCards") instanceof ArrayList<?>)) return false;
		ArrayList<Card> tableCards = (ArrayList<Card>) parameters.get("TableCards");
		int numCards = tableCards.size();
		if (numCards != 0 && numCards != 3 && numCards != 5) return false;
		
		//Validate "You" object
		if (!(parameters.get("You") instanceof HashMap<?, ?>)) return false; //Must be json object
		HashMap<String, Object> you = (HashMap<String, Object>) parameters.get("You");
		
		if (!you.containsKey("Position")) return false;
		if (!(you.get("Position") instanceof Integer)) return false;
		
		if (!you.containsKey("Hand")) return false;
		if (!(you.get("Hand") instanceof ArrayList<?>)) return false;
		ArrayList<Card> yourHand = (ArrayList<Card>) you.get("Hand");
		numCards = tableCards.size();
		if (numCards != 0 && numCards != 2) return false;
		
		if (!you.containsKey("Chips")) return false;
		if (!(you.get("Chips") instanceof Integer)) return false;
		
		//Validate OtherPlayers object
		if (!(parameters.containsKey("OtherPlayers"))) return false;
		if (!(parameters.get("OtherPlayers") instanceof ArrayList<?>)) return false;
		ArrayList<HashMap<String, Object>> playersList = (ArrayList<HashMap<String, Object>>) parameters.get("OtherPlayers");
		for (HashMap<String, Object> player : playersList) {
			if (!isValidPlayer(player, true)) return false;
			if (!(player.containsKey("Position"))) return false;
		}
		
		return true;
	}
	
	private boolean isValidPlayer(HashMap<String, Object> player, boolean requireChips) {
		if (!player.containsKey("Username")) return false;
		if (!(player.get("Username") instanceof String)) return false;
		if (!player.containsKey("Avatar")) return false;
		if (!(player.get("Avatar") instanceof String)) return false;
		if (requireChips) {
			if (!player.containsKey("Chips")) return false;
			if (!(player.get("Chips") instanceof Integer)) return false;
		}
		return true;
	}
	

	
	/**
	 * Valid types of states to send
	 * @author bgreen
	 */
	public enum StateType {
		/** State represents a lobby*/
		LOBBY,
		
		/** State represents a game*/
		GAME		
	}

}
