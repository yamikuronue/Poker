package pokerServer.interfaces;

import pokerServer.Message;

public interface StateObserver extends Observer {
	public void onStateChanged(Message newState);
}
