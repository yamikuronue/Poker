package pokerServer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Instances of the class “PokerServer” shall represent a single concurrent server running. 
 * Each instance of the server shall listen on a specific port. 
 * It shall have the responsibility for managing connections and creating game objects as needed. 
 * This shall be a Singleton object
 * @author bgreen
 *
 */
public class PokerServer {
	/** The maximum players allowed in a game. **/
	public static int MAX_PLAYERS_PER_GAME = 6;
	/**The minimum players needed to run a game **/
	public static final int MIN_PLAYERS_PER_GAME = 3;
	private static Integer nextGameID = 1;
	private static HashMap<Integer, Game> activeGames = new HashMap<Integer, Game>();
	private static HashMap<Integer, Lobby> activeLobbies = new HashMap<Integer, Lobby>();
	private static ArrayList<Player> allPlayers = new ArrayList<Player>();
	
	
	 /**
	  * The main method. Starts the server
	 * @param args Command-line arguments
	 */
	public static void main(String args[]) {
		 //Create a lobby and a game. They will add themselves to the lists.
		 new Lobby();
		 new Game();		
	 }
	
	/**
	 * Get the Game object specified by the unique ID
	 * @param gameID The ID for the game
	 * @return the Game object
	 */
	public static Game getGameFromID(Integer gameID) {
		if (activeGames.containsKey(gameID)) return activeGames.get(gameID);
		throw new IllegalArgumentException("Invalid game ID");
	}
	
	
	/**
	 * Add a Game to the list
	 * @param game The game object
	 * @return The ID for the game
	 */
	public static Integer addGame(Game game) {
		activeGames.put(nextGameID, game);
		return nextGameID++;
	}
	
	/**
	 * Check if a game ID is valid and corresponds to an active game
	 * @param gameID The ID of the game
	 * @return True if there is a game by that ID, false if not.
	 */
	public static boolean gameIDIsValid(Integer gameID) {
		return activeGames.containsKey(gameID);
	}


	/**
	 * Get the lobby that the player should be assigned to.
	 * @return A Lobby that can accept a new player.
	 */
	public static Lobby getLobbyToJoin() {
		return activeLobbies.get(0);
	}
	
}
