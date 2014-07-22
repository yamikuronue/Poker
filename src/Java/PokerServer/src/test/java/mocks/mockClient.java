package mocks;

import pokerServer.Message;
import pokerServer.interfaces.Client;


/**
 * A quick and dirty Client mock.
 * @author bgreen
 */
public class mockClient implements Client {

	/** Whether receiveMessage was invoked yet */
	public boolean receiveMessageInvoked = false;
	/** Whether sendMessage was invoked yet */
	public boolean sendMessageInvoked = false;
	/** The last message parameter handed to sendMessage **/
	public Message lastMessageSent = null;
	
	
	@Override
	public void receiveMessage() {
		receiveMessageInvoked = true;
	}

	@Override
	public void sendMessage(Message message) {
		sendMessageInvoked = true;
		lastMessageSent = message;
	}

}
