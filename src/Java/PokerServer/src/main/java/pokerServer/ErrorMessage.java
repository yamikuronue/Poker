package pokerServer;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “ErrorMessage” represent a message going to a client that encodes an error.  
 * This can represent an Error message only. 
 * @author bgreen
 *
 */
public class ErrorMessage extends Message {

	/**
	 * Default constructor
	 * @param clientInvolved The client receiving the error message
	 */
	public ErrorMessage(Client clientInvolved) {
		super(clientInvolved);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
