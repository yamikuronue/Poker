package pokerServer;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “ErrorMessage” represent a message going to a client that encodes an error.  
 * This can represent an Error message only. 
 * @author bgreen
 *
 */
public class ErrorMessage extends Message {

	public ErrorMessage(Client clientInvolved) {
		super(clientInvolved);
	}

}
