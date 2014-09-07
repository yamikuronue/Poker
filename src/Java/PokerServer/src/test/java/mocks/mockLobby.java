package mocks;

import java.util.ArrayList;

import pokerServer.ActionMessage;
import pokerServer.Lobby;
import pokerServer.Player;
import pokerServer.interfaces.Observer;
import pokerServer.interfaces.StateObserver;

public class mockLobby extends Lobby {
	public ArrayList<StateObserver> observers;

	public mockLobby() {
		super();
		observers = new ArrayList<StateObserver>();
	}
	
	@Override
	public boolean addObserver(Observer observer) {
		if (observer instanceof StateObserver) {
			observers.add((StateObserver) observer);
			return true;
		}
		throw new IllegalArgumentException("Not the right kind of observer");
	}

	@Override
	public boolean removeObserver(Observer observer) {
		if (observer instanceof StateObserver) {
			observers.remove((StateObserver) observer);
			return true;
		}
		throw new IllegalArgumentException("Not the right kind of observer");
	}
}
