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
	HashMap<String, Object> parameters;
	
	/**
	 * Constructor to create a message for a client.
	 * @param clientInvolved The client that the message is from or to.
	 */
	public StateMessage(StateType type, Client clientInvolved) {
		super(clientInvolved);
		parameters = new HashMap<String, Object>();
		this.type = type;
	}
	
	/**
	 * Add a parameter to the message state. This is safe and will not overwrite existing parameters
	 * @param name The name of the parameter to add
	 * @param value The value to set
	 * @return True if it succeeded, false if not. It will return false if the parameter already existed.
	 */
	public boolean addParameter(String name, Object value) {
		if (parameters.containsKey(name)) return false;
		parameters.put(name, value);
		return true;
	}
	
	/**
	 * Remove a parameter from the message
	 * @param name The parameter to remove
	 * @return True for a success. 
	 */
	public boolean removeParameter(String name) {
		parameters.remove(name);
		return true;
	}
	
	/**
	 * Set a parameter to a value, whether it previously existed or not. 
	 * @param name The name of the parameter to set
	 * @param value The value to set
	 * @return True if it succeeded, false if not
	 */
	public boolean setParameter(String name, Object value) {
		if (parameters.containsKey(name)) parameters.replace(name, value);
		else parameters.put(name, value);
		return true;
	}
	
	/**
	 * Computes if the parameters given to the message are complete and valid according to the spec
	 * @return True if so, false if it is not valid. 
	 */
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
	
	private boolean isValidPlayer(HashMap<String, Object> player, boolean requireChips) {
		if (!player.containsKey("username")) return false;
		if (!(player.get("username") instanceof String)) return false;
		if (!player.containsKey("avatar")) return false;
		if (!(player.get("avatar") instanceof String)) return false;
		if (requireChips) {
			if (!player.containsKey("chips")) return false;
			if (!(player.get("chips") instanceof Integer)) return false;
		}
		return true;
	}
	
	private boolean gameMessageIsValid() {
		return false;
	}
	
	public enum StateType {
		/** State represents a lobby*/
		LOBBY,
		
		/** State represents a game*/
		GAME		
	}

}
