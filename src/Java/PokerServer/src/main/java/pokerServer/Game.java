package pokerServer;

import java.util.ArrayList;
import java.util.HashMap;

import pokerServer.interfaces.Observable;
import pokerServer.interfaces.Observer;
import pokerServer.interfaces.StateObserver;

/**
 * Instances of the class “Game” shall represent a single game currently in progress. 
 * It shall handle the turns of the game, including enforcing the business rules. 
 * There is the possibility of observing a game without being a player in it, though no client currently implements this behavior.
 * @author bgreen
 *
 */
public class Game implements Observable {

	protected Integer ID;
	private ArrayList<StateObserver> observers;
	private HashMap<Integer, Player> players;
	private GameState state;
	/**
	 * Create a new game and add it to the list
	 */
	public Game() {
		ID = PokerServer.addGame(this);
		observers = new ArrayList<StateObserver>();
		players = new HashMap<Integer, Player>();
		this.state = GameState.WAITING_FOR_PLAYERS;
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

	/**
	 * Add a player to the game
	 * @param player The player to add
	 * @return True if the add succeeded, false if it did not. Usually if it did not succeed, the game is full.
	 */
	public boolean addPlayer(Player player) {
		//Here we have to iterate over the option positions
		//because when people leave, they leave an open seat
		//so we will want to fill open seats
		
		for (int i = 0; i < PokerServer.MAX_PLAYERS_PER_GAME; i++) {
			if (!players.containsKey(i)) {
				players.put(i, player);
				return true;
			};
		}
		
		if (state == GameState.WAITING_FOR_PLAYERS && players.size() >= PokerServer.MIN_PLAYERS_PER_GAME) {
			dealHands();
		}
		
		return false;
	}

	private void dealHands() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Remove a player from the game
	 * @param player The player to add
	 * @return True if the removal succeeded, false if it did not (usually because you're not in the game)
	 */
	public boolean removePlayer(Player player) {
		for (Integer position : players.keySet()) {
			if (players.get(position) == player) {
				players.remove(position);
				return true;
			}
		}
		return false;
	}

	public boolean parseMessage(ActionMessage am) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}
	
	public enum GameState {
		WAITING_FOR_PLAYERS,
		DEALING,
		BETTING,
		END_GAME
	}

}