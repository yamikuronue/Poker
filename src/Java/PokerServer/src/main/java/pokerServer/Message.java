package pokerServer;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “Message” represent a message coming from or going to a client. 
 * @author bgreen
 */
public abstract class Message {
	Client clientInvolved;

	public Message(Client clientInvolved) {
		super();
		this.clientInvolved = clientInvolved;
	}
	
}
