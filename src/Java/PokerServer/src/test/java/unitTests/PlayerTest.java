package unitTests;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

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
	
}
