package pokerServer.interfaces;

import pokerServer.Message;

public interface Client {
	public void receiveMessage();
	public void sendMessage(Message message);
}
