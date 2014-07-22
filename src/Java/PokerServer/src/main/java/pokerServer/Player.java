package pokerServer;

import pokerServer.interfaces.ClientObserver;
import pokerServer.interfaces.StateObserver;

public class Player implements StateObserver, ClientObserver {

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public void onStateChanged(Message newState) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

}
