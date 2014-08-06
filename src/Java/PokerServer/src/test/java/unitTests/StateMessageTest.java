package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import pokerServer.Card;
import pokerServer.StateMessage;
import pokerServer.StateMessage.StateType;

/**
 * Unit tests for state messages. The only logic here is the validation routine. 
 * @author bgreen
 *
 */
public class StateMessageTest {

	/* Meta Tests */ 
	/**
	 * Verifies that an invalid state type makes a state message invalid.
	 * Rule: State type must be one of "Lobby" or "Game"
	 */
	@Test
	public void invalidStateFailsValidation() {
		StateMessage oot = new StateMessage(null, null);
		assertFalse(oot.isValid());
	}
	
	/* Lobby Tests */ 
	/**
	 * Verifies that missing parameters in the lobby makes a state message invalid
	 * Rule: Any Lobby state message must include a list of games and a list of occupants.
	 */
	@Test
	public void missingLobbyParamsFailValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);
		
		//No params
		assertFalse(oot.isValid());
		
		//One of two top-level params
		oot.addParameter("Games", new ArrayList<Object>());
		assertFalse(oot.isValid());
		
		//Both top-level params
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that invalid games in the lobby state message make the message invalid
	 * Rule: Games must include an ID, whether they are open, and a list of players. 
	 */
	@Test
	public void invalidGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		
		game.put("InvalidKey", null);
		gamesList.add(game);	
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that an invalid "open" parameter in a game in the lobby state message invalidates the message
	 * Rule: Valid values for the "open" parameter are "true" and "false". This is encoded as a Boolean object.
	 */
	@Test
	public void invalidOpenStateinGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		
		game.put("ID", "12345");
		game.put("Open", "NotABoolen");
		game.put("Players", playersList);
		gamesList.add(game);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a game without an id in the lobby state message invalidates the message
	 * Rule: All games must have an ID 
	 */
	@Test
	public void missingIDinGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		
		game.put("Open", new Boolean(false));
		game.put("Players", playersList);
		gamesList.add(game);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a game without an id in the lobby state message invalidates the message
	 * Rule: All games must have an ID 
	 */
	@Test
	public void extraParamsinGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		
		game.put("ID", "12345");
		game.put("Open", new Boolean(false));
		game.put("Players", playersList);
		game.put("NotAValidParam", null);
		gamesList.add(game);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a game which is valid does not cause a message to fail validation
	 */
	@Test
	public void validGameInLobbyPassesValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		
		game.put("ID", "12345");
		game.put("Open", new Boolean(false));
		game.put("Players", playersList);
		gamesList.add(game);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", new ArrayList<Object>());
		
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that invalid players in a lobby state message make the message invalid.
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void invalidPlayerInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		game.put("Players", playersList);
		player.put("InvalidKey", null);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without a name in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoNameInGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Avatar", null);
		player.put("Chips", new Integer(12));
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without an avatar in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoAvatarInGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Username", "test");
		player.put("Chips", new Integer(12));
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without a chip count in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoChipsInGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Avatar", null);
		player.put("Username", "test");
		game.put("Players", playersList);
		gamesList.add(game);		
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player with a non-numeric chip count in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerInvalidChipsInGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Username", "test");
		player.put("Avatar", null);
		player.put("Chips", "Fritos");
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that invalid players in a lobby state message make the message invalid.
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void invalidPlayerinGameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("InvalidKey", null);
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without a name in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoNameInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Avatar", null);
		player.put("Chips", new Integer(12));
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without an avatar in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoAvatarInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Username", "test");
		player.put("Chips", new Integer(12));
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player without a chip count in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerNoChipsInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Avatar", null);
		player.put("Username", "test");
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player with a non-numeric chip count in a lobby state message makes the message invalid
	 * Rule: Players must contain a username, an avatar, and a number of chips
	 */
	@Test
	public void playerInvalidChipsInLobbyFailsValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Username", "test");
		player.put("Avatar", null);
		player.put("Chips", "Fritos");
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a player that is valid can be in the lobby
	 */
	@Test
	public void validPlayerPassesValidation() {
		StateMessage oot = new StateMessage(StateType.LOBBY, null);

		ArrayList<HashMap<String, Object>> gamesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> game = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> player = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> occupantList = new ArrayList<HashMap<String, Object>>();

		
		game.put("ID", "12345");
		game.put("Open", new Boolean(true));
		player.put("Username", "test");
		player.put("Avatar", null);
		player.put("Chips", new Integer(12));
		game.put("Players", playersList);
		gamesList.add(game);
		occupantList.add(player);
		oot.addParameter("Games", gamesList);
		oot.addParameter("LobbyOccupants", occupantList);
		
		assertFalse(oot.isValid());
	}

	/* Game Tests */ 
	/**
	 * Verifies that a game state message without parameters is invalid
	 */
	@Test
	public void invalidGameFailsValidation() {
		StateMessage oot = new StateMessage(StateType.GAME, null);
		assertFalse(oot.isValid());
	}
	
	
	
	
	/**
	 * Verifies that a game state message without parameters is invalid
	 */
	//@Test  //This is work in progress, do not test yet. 
	public void validGamePassesValidation() {
		StateMessage oot = new StateMessage(StateType.GAME, null);
		
		//Two player game. You are A.
		HashMap<String, Object> playerA = new HashMap<String, Object>();
		HashMap<String, Object> playerB = new HashMap<String, Object>();
		playerA.put("Position", new Integer(1));
		playerB.put("Position", new Integer(2));
		playerA.put("Chips", new Integer(10));
		playerB.put("Chips", new Integer(20));
		playerB.put("Username", "PlayerB");
		playerB.put("Avatar", null);
		playerA.put("Hand", new ArrayList<Card>());
		ArrayList<HashMap<String, Object>> otherPlayers = new ArrayList<HashMap<String, Object>>();
		otherPlayers.add(playerB);
		
		//State is beginning of game, pre-deal.
		oot.addParameter("Pot", new Integer(0));
		oot.addParameter("Dealer", "playerA");
		oot.addParameter("Actor", "playerA");
		oot.addParameter("TableCards", new ArrayList<Card>());
		oot.addParameter("You", playerA);
		oot.addParameter("OtherPlayers", otherPlayers);
		oot.addParameter("LastAction", null);
		
		assertTrue(oot.isValid());
	}
}
