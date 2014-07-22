package pokerServer;

import pokerServer.interfaces.Client;
import pokerServer.interfaces.Observable;
import pokerServer.interfaces.Observer;

public class ClientSocket implements Client, Observable {

	@Override
	public boolean addObserver(Observer observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public boolean removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public void receiveMessage() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

}
