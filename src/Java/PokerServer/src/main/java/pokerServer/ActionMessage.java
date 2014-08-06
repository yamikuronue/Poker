package pokerServer;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “ActionMessage” represent a message coming from a client.  
 * This can represent a Player Action, Authentication Attempt, or Create User action.
 * @author bgreen
 *
 */
public class ActionMessage extends Message {

	public ActionMessage(Client clientInvolved) {
		super(clientInvolved);
	}

}
