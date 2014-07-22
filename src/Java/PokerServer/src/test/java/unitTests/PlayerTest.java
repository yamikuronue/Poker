package unitTests;

import static org.junit.Assert.*;
import mocks.mockClient;

import org.junit.Test;

import pokerServer.Card;
import pokerServer.Player;
import pokerServer.Card.Suit;

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

}
