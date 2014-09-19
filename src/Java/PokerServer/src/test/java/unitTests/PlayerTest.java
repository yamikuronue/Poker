package unitTests;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import mocks.mockClient;
import mocks.mockGame;
import mocks.mockLobby;

import org.junit.Test;

import pokerServer.ActionMessage;
import pokerServer.ActionMessage.Action;
import pokerServer.Card;
import pokerServer.Player;
import pokerServer.StateMessage;
import pokerServer.Card.Suit;
import pokerServer.StateMessage.StateType;

/**
 * Unit tests for the Player class
 * @author bgreen
 *
 */
public class PlayerTest {


	/**
	 * Tests that cards added to the hand are retained and regurgitated on command
	 */
	@Test
	public void addToHandTest() {
		mockClient client = new mockClient(); 
		Player player = new Player("user", "user@example.com", client, 100);
		
		assertEquals(0, player.getHand().size());
		
		Card card1 = new Card(Suit.DIAMONDS, "Q");
		player.addCardToHand(card1);
		
		assertEquals(1, player.getHand().size());
		assertTrue(player.getHand().contains(card1));
		
		Card card2 = new Card(Suit.CLUBS, "Q");
		player.addCardToHand(card2);
		
		assertEquals(2, player.getHand().size());
		assertTrue(player.getHand().contains(card2));
		
	}
	
	/**
	 * Tests that when the player is asked to empty their hand, they don't cheat and retain cards
	 */
	@Test
	public void emptyHandTest() {
		mockClient client = new mockClient(); 
		Player player = new Player("user", "user@example.com", client, 100);
		
		assertEquals(0, player.getHand().size());
		
		Card card1 = new Card(Suit.DIAMONDS, "Q");
		player.addCardToHand(card1);
		Card card2 = new Card(Suit.CLUBS, "Q");
		player.addCardToHand(card2);
		
		player.resetHand();
		assertEquals(0, player.getHand().size());
		
	}

	/**
	 * Tests that a gravatar is successfully created
	 */
	@Test
	public void gravatarTest() {
		String email = "test@test.com";
		String md5_email = "b642b4217b34b1e8d3bd915fc65c4452"; //Precomputed to avoid duplicating logic 
		String expected = "http://www.gravatar.com/avatar/" + md5_email;
		
		mockClient client = new mockClient();
		Player oot = new Player("test", email, client, 100);
		
		String actual = oot.getAvatarURL().toString();
		
		assertEquals("Avatar url not generated correctly",expected,actual);
	}
	
	/**
	 * Tests that the gravatar formula is implemented correctly to produce an image when fetched.
	 * This test works by attempting to fetch and failing if we hit some kind of error,
	 * such as a 400 or a 404 from the server
	 */
	@Test
	public void gravatarFetchTest() {
		String email = "test@test.com";
		mockClient client = new mockClient();
		Player oot = new Player("test", email, client, 100);
		
		URL avatarURL = oot.getAvatarURL();
		
		try {
			@SuppressWarnings("unused")
			Image avatar = ImageIO.read(avatarURL);
		} catch (IOException e) {
			fail("Error getting image: " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Tests that the Player puts the right information in the "You" column
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void playerAddsCorrectHandAndChipsToStateMessage() {
		//First we need a player
		String email = "test@test.com";
		mockClient client = new mockClient();
		Player oot = new Player("PlayerA", email, client, 100);
		
		//Now configure the hand
		Card card1 = new Card(Suit.DIAMONDS, "Q");
		oot.addCardToHand(card1);
		
		Card card2 = new Card(Suit.CLUBS, "Q");
		oot.addCardToHand(card2);
		
		
		//Now make a GameState message
		StateMessage messageBefore = createTwoPlayerGameState();
		
		//Pass it to the Player
		oot.onStateChanged(messageBefore);
		
		//First ensure the Player passed it on
		assertTrue(client.sendMessageInvoked);
		
		StateMessage messageAfter = (StateMessage) client.lastMessageSent;
		
		//Now check the message		
		HashMap<String, Object> you = null;
		try {
			you = (HashMap<String, Object>) messageAfter.getParameter("You");
		} catch(IllegalArgumentException e) {
			fail("Could not find 'you' parameter");
		}
		
		//Now check correctness
		assertTrue(you.containsKey("Chips"));
		Integer numChips = Integer.parseInt(you.get("Chips").toString());
		assertEquals(100, numChips.intValue());
		
		assertTrue(you.containsKey("Hand"));
		ArrayList<Card> hand = (ArrayList<Card>) you.get("Hand");
		assertTrue(hand.contains(card1));
		assertTrue(hand.contains(card2));
		
	}
	
	private StateMessage createTwoPlayerGameState() {
		StateMessage message = new StateMessage(StateType.GAME, null);
		
		//Two player game. You are A.
		HashMap<String, Object> playerA = new HashMap<String, Object>();
		HashMap<String, Object> playerB = new HashMap<String, Object>();
		playerA.put("Position", new Integer(1));
		playerB.put("Position", new Integer(2));
		playerB.put("Chips", new Integer(20));
		playerB.put("Username", "PlayerB");
		playerB.put("Avatar", "");
		ArrayList<HashMap<String, Object>> otherPlayers = new ArrayList<HashMap<String, Object>>();
		otherPlayers.add(playerB);
		
		//State is beginning of game, pre-deal.
		message.addParameter("Pot", new Integer(0));
		message.addParameter("Dealer", "playerA");
		message.addParameter("Actor", "playerA");
		message.addParameter("You", playerA);
		message.addParameter("TableCards", new ArrayList<Card>());
		message.addParameter("OtherPlayers", otherPlayers);
		message.addParameter("LastAction", null);
		
		return message;
}
	
	
	/**
	 * Verifies that a player can join a Game given a Game to join
	 */	
	@Test
	public void playerCanJoinGame() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins the game
		mockGame game = new mockGame();
		assertTrue(oot.joinGame(game));
		
		assertTrue(game.observers.contains(oot));
		assertTrue(game.players.contains(oot));
	}
	
	/**
	 * Verifies that the JoinGame method returns false when the game is not joined
	 */	
	@Test
	public void playerCanFailToJoinGame() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins the game
		mockGame game = new mockGame();
		game.actionReturnValue = false;
		assertFalse(oot.joinGame(game));
	}
	
	/**
	 * Verifies that a player can join a Game given a Game to join
	 */	
	@Test
	public void playerCanJoinGameWhileInGame() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins game 1
		mockGame game1 = new mockGame();
		oot.joinGame(game1);
		
		//Then game 2
		mockGame game2 = new mockGame();
		oot.joinGame(game2);
		
		assertTrue(game2.observers.contains(oot));
		assertTrue(game2.players.contains(oot));
		assertFalse(game1.observers.contains(oot));
		assertFalse(game1.players.contains(oot));
	}
	
	/**
	 * Verifies that a player can join a Lobby given a Lobby to join
	 */	
	@Test
	public void playerCanJoinLobby() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins the lobby
		mockLobby lobby = new mockLobby();
		assertTrue(oot.joinLobby(lobby));
		
		assertTrue(lobby.observers.contains(oot));
	}
	
	/**
	 * Verifies that a player returns false when a lobby is not joined
	 */	
	@Test
	public void playerCanFailToJoinLobby() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins the lobby
		mockLobby lobby = new mockLobby();
		lobby.actionReturnValue = false;
		assertFalse(oot.joinLobby(lobby));
	}
	
	/**
	 * Verifies that a player can join a Lobby given a Lobby to join
	 */	
	@Test
	public void playerCanJoinGameAfterLobby() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
				
		//Player joins the lobby
		mockLobby lobby = new mockLobby();
		oot.joinLobby(lobby);
		
		//Then the game
		mockGame game = new mockGame();
		oot.joinGame(game);
		
		//Results
		assertTrue(game.observers.contains(oot));
		assertTrue(game.players.contains(oot));
		assertFalse(lobby.observers.contains(oot));
		
	}
	
	/**
	 * Verifies that a player can join a Game given a GameID in an ActionMessage
	 */	
	@Test
	public void playerCanJoinGameFromID() {
		//First, we need a client
		mockClient client = new mockClient();
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
		
		//To join, we need a message saying to do so
		ActionMessage joinMessage = new ActionMessage(Action.JOIN, client);
				
		//And a game to join
		mockGame game = new mockGame();
		joinMessage.addParameter("GameID", game.gameID);
		
		//Hand the message to the player
		oot.onMessageReceived(joinMessage);

		//And verify the results
		assertTrue(game.observers.contains(oot));
		assertTrue(game.players.contains(oot));
	}
	
	/**
	 * Verifies that a player can respond to a Quit message successfully
	 */
	@Test
	public void playerCanQuitGame() {
		//First, we need a client
		mockClient client = new mockClient();
		
		//To quit, we need a message saying to do so
		ActionMessage quitMessage = new ActionMessage(Action.QUIT, client);
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
		
		//Player needs to be in the game
		mockGame game = new mockGame();
		oot.joinGame(game);
		
		//We pass in the message as though we were the client
		oot.onMessageReceived(quitMessage);
		
		//Player should exit game
		assertFalse(game.observers.contains(oot));
		assertFalse(game.players.contains(oot));
	}
	
	/**
	 * Verifies that a player can respond to a Fold message successfully when not Quitting
	 */
	@Test
	public void playerCanFoldNoQuit() {
		//First, we need a client
		mockClient client = new mockClient();
		
		//To fold, we need a message saying to do so
		ActionMessage foldMessage = new ActionMessage(Action.FOLD, client);
		foldMessage.addParameter("Quit", false);
		
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
		
		//Player needs to be in the game
		mockGame game = new mockGame();
		oot.joinGame(game);
		
		//We pass in the message as though we were the client
		oot.onMessageReceived(foldMessage);
		
		//Player should not exit game
		assertTrue(game.observers.contains(oot));
		assertTrue(game.players.contains(oot));
		
		//But should send on the "fold" command.
		assertEquals(foldMessage, game.lastActionMessage);
	}
	
	/**
	 * Verifies that a player can respond to a Fold message successfully when also Quitting
	 */
	@Test
	public void playerCanFoldAndQuit() {
		//First, we need a client
		mockClient client = new mockClient();
		
		//To fold, we need a message saying to do so
		ActionMessage foldMessage = new ActionMessage(Action.FOLD, client);
		foldMessage.addParameter("Quit", true);
		
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
		
		//Player needs to be in the game
		mockGame game = new mockGame();
		oot.joinGame(game);
		
		//We pass in the message as though we were the client
		oot.onMessageReceived(foldMessage);
		
		//Player should exit game
		assertFalse(game.observers.contains(oot));
		assertFalse(game.players.contains(oot));
		
		//But should also send on the "fold" command.
		assertEquals(foldMessage, game.lastActionMessage);
	}
	
	/**
	 * Verifies that a player can respond to a Bet message successfully
	 */
	@Test
	public void playerCanBet() {
		//First, we need a client
		mockClient client = new mockClient();
		
		//To bet, we need a message saying to do so
		ActionMessage betMessage = new ActionMessage(Action.BET, client);
		betMessage.addParameter("Amount", 100);
		betMessage.addParameter("All-in", false);
		
				
		//And a player
		Player oot = new Player("PlayerA", "Test@test.com", client, 100);
		
		//Player needs to be in the game
		mockGame game = new mockGame();
		oot.joinGame(game);
		
		//We pass in the message as though we were the client
		oot.onMessageReceived(betMessage);
		
		//Player should not exit game
		assertTrue(game.observers.contains(oot));
		assertTrue(game.players.contains(oot));
		
		//Player should send on the "bet" command.
		assertEquals(betMessage, game.lastActionMessage);
	}
}
