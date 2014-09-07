package pokerServer;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “ActionMessage” represent a message coming from a client.  
 * This can represent a Player Action, Authentication Attempt, or Create User action.
 * @author bgreen
 *
 */
public class ActionMessage extends Message {

	/**
	 * The action to perform 
	 */
	public final Action action;
	
	/**
	 * Default constructor
	 * @param action  The type of action to perform
	 * @param clientInvolved The client involved in the message
	 */
	public ActionMessage(Action action, Client clientInvolved) {
		super(clientInvolved);
		this.action = action;
	}
	

	/**
	 * Types of actions that can be performed
	 * @author bgreen
	 */
	public enum Action {
		/** Join a game **/
		JOIN,
		/**Place a bet **/
		BET,
		/** Fold current hand **/
		FOLD,
		/** Quit game altogether **/
		QUIT
	}
	
	@Override
	public boolean isValid() {
		if (action ==  null) return false;
		
		switch (action) {
		case BET:
			return isValidBetMessage();
		case FOLD:
			return isValidFoldMessage();
		case JOIN:
			return isValidJoinMessage();
		case QUIT:
			return isValidQuitMessage();
		default:
			return false;
		}
	}
	
	private boolean isValidJoinMessage() {
		if (parameters.size() != 1) return false;
		if (parameters.containsKey("GameID")) {
			if (parameters.get("GameID") instanceof Integer) return true;
			else return false;
		} else {
			return false;
		}
	}
	
	private boolean isValidBetMessage() {
		if (parameters.size() != 2) return false;
		if (parameters.containsKey("Amount")) {
			if (!(parameters.get("Amount") instanceof Integer)) return false;
		} else {
			return false;
		}
		
		if (parameters.containsKey("All-in")) {
			if (!(parameters.get("All-in") instanceof Boolean)) return false;
		} else {
			return false;
		}
		
		return true;
	}
	
	private boolean isValidFoldMessage() {
		if (parameters.size() != 1) return false;
		if (parameters.containsKey("Quit")) {
			if (parameters.get("Quit") instanceof Boolean) return true;
			else return false;
		} else {
			return false;
		}
	}
	
	private boolean isValidQuitMessage() {
		if (parameters.size() == 0) return true;
		return false;
	}

}
