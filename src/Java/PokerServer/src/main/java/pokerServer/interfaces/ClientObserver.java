package pokerServer.interfaces;

import pokerServer.Message;

/**
 * This represents something that observes a client. It defines a common callback to be used. 
 * @author bgreen
 *
 */
public interface ClientObserver extends Observer {
	/**
	 * The callback for a message being received. 
	 * @param message The message received
	 */
	public void onMessageReceived(Message message);
}
