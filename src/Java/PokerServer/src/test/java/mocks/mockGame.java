package mocks;

import java.util.ArrayList;

import pokerServer.ActionMessage;
import pokerServer.Game;
import pokerServer.Player;
import pokerServer.interfaces.*;

public class mockGame extends Game {
	public ArrayList<StateObserver> observers;
	public ArrayList<Player> players;
	public ActionMessage lastActionMessage;
	public Integer gameID;
	public boolean actionReturnValue = true;
	public int positionToReturn = 0;

	public mockGame() {
		super();
		observers = new ArrayList<StateObserver>();
		players = new ArrayList<Player>();
		this.gameID = this.ID;
	}
	
	@Override
	public boolean addObserver(Observer observer) {
		if (observer instanceof StateObserver) {
			observers.add((StateObserver) observer);
			return actionReturnValue;
		}
		throw new IllegalArgumentException("Not the right kind of observer");
	}

	@Override
	public boolean removeObserver(Observer observer) {
		if (observer instanceof StateObserver) {
			observers.remove((StateObserver) observer);
			return actionReturnValue;
		}
		throw new IllegalArgumentException("Not the right kind of observer");
	}
	
	@Override
	public Integer getPositionFor(Player p) {
		return positionToReturn;
	}

	public boolean addPlayer(Player player) {
		players.add(player);
		return actionReturnValue;
	}
	
	public boolean removePlayer(Player player) {
		players.remove(player);
		return actionReturnValue;
	}
	
	public boolean parseMessage(ActionMessage message) {
		this.lastActionMessage = message;
		return actionReturnValue;
	}
}
