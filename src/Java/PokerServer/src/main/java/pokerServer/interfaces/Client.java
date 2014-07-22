package pokerServer.interfaces;

import pokerServer.Message;

/**
 * This represents a client that can send and receive messages
 * @author bgreen
 *
 */
public interface Client {
	/** The method invoked when a message is received */
	public void receiveMessage();
	
	/**
	 * The method invoked in order to send a message 
	 * @param message The message to send
	 */
	public void sendMessage(Message message);
}
