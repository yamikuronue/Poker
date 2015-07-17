package pokerServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import pokerServer.ActionMessage.Action;
import pokerServer.StateMessage.StateType;
import pokerServer.interfaces.Observable;
import pokerServer.interfaces.Observer;
import pokerServer.interfaces.StateObserver;

/**
 * Instances of the class �Game� shall represent a single game currently in progress. 
 * It shall handle the turns of the game, including enforcing the business rules. 
 * There is the possibility of observing a game without being a player in it, though no client currently implements this behavior.
 * @author bgreen
 *
 */
public class Game implements Observable {

	protected Integer ID;
	private ArrayList<StateObserver> observers;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Player> stillInGame;
	private GameState state;
	private ArrayList<Card> deck;
	private Random rand;
	private Integer pot;
	private Player dealer;
	private Player currentActor;
	private ArrayList<Card> tableCards;
	private ActionMessage lastAction;
	
	/**
	 * Create a new game and add it to the list
	 */
	public Game() {
		ID = PokerServer.addGame(this);
		observers = new ArrayList<StateObserver>();
		players = new HashMap<Integer, Player>();
		this.state = GameState.WAITING_FOR_PLAYERS;
		this.rand = new Random();
		this.pot = 0;
		this.tableCards = new ArrayList<Card>();
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
			return observers.remove((StateObserver) observer);
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
				if (state == GameState.WAITING_FOR_PLAYERS && players.size() >= PokerServer.MIN_PLAYERS_PER_GAME) {
					startGame();
				}
				return true;
			};
		}
		
		return false;
	}
	
	/**
	 * Parse a message representing an action to be taken
	 * @param am The message
	 * @param player The player taking the action
	 * @return True if the action was taken, false if not. 
	 */
	public boolean parseMessage(ActionMessage am, Player player) {
		//Check if the player can act
		if (player != currentActor) {
			return false;
		}
		
		int position = getPositionFor(player);
		
		//That just leaves betting and folding
		if (am.action == Action.FOLD) {
			stillInGame.remove(position);
			
			//Check for end of game resulting from this action
			if (stillInGame.size() <= 1) {
				calculateWinner();
			}
		} else if (am.action == Action.BET){
			//Betting
			Integer amount = (Integer) am.getParameter("Amount");
			
			//Player already checked if they have enough
			//So we just add it to the pot
			pot += amount;			 
		} else {
			return false;
		}
		
		//Continue on
		currentActor = getNextValidPlayer(position);
		messageStateChanged();
		return true;
	}
	
	private Player getNextValidPlayer(int lastPosition) {
		for (int i = lastPosition+1; i <= stillInGame.size(); i++) {
			if (stillInGame.containsKey(i)) {
				return stillInGame.get(i);
			}
		}
		
		//Wrap around
		for (int i = 0; i <= lastPosition; i++) {
			if (stillInGame.containsKey(i)) {
				return stillInGame.get(i);
			}
		}
		
		throw new IllegalStateException("No players can act!");
	}

	private void startGame() {
		//Game starts! 
		stillInGame = new HashMap<Integer,Player>();
		for (int i = 0; i < players.size(); i++) {
			stillInGame.put(i, players.get(i));
		}
		
		//Pick dealer
		int lastDealerPosition = 0;
		if (dealer != null) {
			lastDealerPosition = getPositionFor(dealer);
			dealer = null;
		}
		
		dealer = getNextValidPlayer(lastDealerPosition+1);
		
		//null out other variables
		tableCards = new ArrayList<Card>();
		lastAction = null;
		pot = 0;
		deck = Card.generateDeck();
				
		//Dealers left goes first
		currentActor = getNextValidPlayer(lastDealerPosition+2);
		
		//Start with betting
		state = GameState.PLAYING;
		messageStateChanged();
	}

	private void dealHands() {
		//Deal each player two cards
		for (int i = 0; i < players.size(); i++) {
			Card card = deck.remove(rand.nextInt(deck.size()));
			players.get(i).addCardToHand(card);
			
			card = deck.remove(rand.nextInt(deck.size()));
			players.get(i).addCardToHand(card);
		}
		
		messageStateChanged();
	}
	
	private void dealTableCard() {
		Card card = deck.remove(rand.nextInt(deck.size()));
		tableCards.add(card);
		messageStateChanged();
	}
	
	private void calculateWinner() {
		//TODO
		
		messageStateChanged();
	}
	
	private void messageStateChanged() {		
		StateMessage message = new StateMessage(StateType.GAME, null);
		ArrayList<HashMap<String, Object>> playersEncoded = new ArrayList<HashMap<String, Object>>();
		
		//Static elements
		message.addParameter("Pot", pot);
		message.addParameter("Dealer", dealer);
		message.addParameter("Actor", currentActor.getUsername());
		message.addParameter("TableCards", tableCards);
		message.addParameter("LastAction", lastAction);
		
		//Encode each player
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			HashMap<String,Object> playerHash = new HashMap<String, Object>();
			playerHash.put("Position", i);
			playerHash.put("Username", player.getUsername());
			playerHash.put("Avatar", player.getAvatarURL());
			playerHash.put("Chips", player.getChips());
			
			playersEncoded.add(playerHash);
		}
		
		message.addParameter("OtherPlayers", playersEncoded);
		
		//send 
		for (StateObserver observer : observers) {
			//TODO: thread
			observer.onStateChanged(message);
		}
	}
	
	/**
	 * Get the position for a given player
	 * @param p The player
	 * @return The position, or null if they are not in the game
	 */
	public Integer getPositionFor(Player p) {
		for (Integer position : players.keySet()) {
			if (players.get(position) == p) {
				return position;
			}
		}
		return null;
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

	
	
	/**
	 * The potential states a game can be in
	 * @author bgreen
	 *
	 */
	public enum GameState {
		/**Waiting for more players to join before starting **/
		WAITING_FOR_PLAYERS,
		/** Game in progress **/
		PLAYING
	}

}