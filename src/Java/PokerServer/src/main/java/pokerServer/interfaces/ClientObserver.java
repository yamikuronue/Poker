package pokerServer.interfaces;

import pokerServer.Message;

public interface ClientObserver extends Observer {
	public void onMessageReceived(Message message);
}
