/**
 * 
 */
package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pokerServer.Card;
import pokerServer.Card.Suit;

/**
 * @author Bayley
 *
 */
public class CardTest {

	/**
	 * Tests that the constructor successfully constructs a valid object
	 */
	@Test
	public void testConstructor() {
		Card newCard = new Card(Suit.HEARTS, "2");
		assertTrue(newCard instanceof Card);
	}
	
	/**
	 * Tests that the constructor assigns the suit I requested to the finished object
	 */
	@Test
	public void suitIsAssignedCorrectly() {
		Card hearts = new Card(Suit.HEARTS, "2");
		Card diamonds = new Card(Suit.DIAMONDS, "2");
		Card spades = new Card(Suit.SPADES, "2");
		Card clubs = new Card(Suit.CLUBS, "2");
		
		assertEquals(hearts.getSuit(), Suit.HEARTS);
		assertEquals(diamonds.getSuit(), Suit.DIAMONDS);
		assertEquals(spades.getSuit(), Suit.SPADES);
		assertEquals(clubs.getSuit(), Suit.CLUBS);
	}
	
	/**
	 * Tests that the constructor assigns the value I requested to the finished object
	 */
	@Test 
	public void valueIsAssignedCorrectly() {
		Card ace = new Card(Suit.HEARTS, "A");
		Card two = new Card(Suit.HEARTS, "2");
		Card three = new Card(Suit.HEARTS, "3");
		Card four = new Card(Suit.HEARTS, "4");
		Card five = new Card(Suit.HEARTS, "5");
		Card six = new Card(Suit.HEARTS, "6");
		Card seven = new Card(Suit.HEARTS, "7");
		Card eight = new Card(Suit.HEARTS, "8");
		Card nine = new Card(Suit.HEARTS, "9");
		Card ten = new Card(Suit.HEARTS, "10");
		Card jack = new Card(Suit.HEARTS, "J");
		Card queen = new Card(Suit.HEARTS, "Q");
		Card king = new Card(Suit.HEARTS, "K");
		
		assertEquals(ace.getValue(), "A");
		assertEquals(two.getValue(), "2");
		assertEquals(three.getValue(), "3");
		assertEquals(four.getValue(), "4");
		assertEquals(five.getValue(), "5");
		assertEquals(six.getValue(), "6");
		assertEquals(seven.getValue(), "7");
		assertEquals(eight.getValue(), "8");
		assertEquals(nine.getValue(), "9");
		assertEquals(ten.getValue(), "10");
		assertEquals(jack.getValue(), "J");
		assertEquals(queen.getValue(), "Q");
		assertEquals(king.getValue(), "K");
	}
	
	/**
	 * Tests that the constructor rejects an invalid number for a value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void invalidNumericValueRejected() {
			new Card(Suit.HEARTS, "15");
	}

	/**
	 * Tests that the constructor rejects an invalid letter for a value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void invalidFaceCardValueRejected() {
			new Card(Suit.HEARTS, "F");
	}
	
	/**
	 * Tests that a deck generates sucessfully. Specifically, that it has 52 cards, that it has 13 of each suit, and that it has 4 of each value
	 */
	@Test
	public void deckGeneratesCorrectly() {
		ArrayList<Card> deck = Card.generateDeck();
		
		//A deck has 52 cards
		assertEquals(52, deck.size());
		
		//13 of each suit
		assertEquals(13, getNumCards(deck, Suit.HEARTS));
		assertEquals(13, getNumCards(deck, Suit.CLUBS));
		assertEquals(13, getNumCards(deck, Suit.DIAMONDS));
		assertEquals(13, getNumCards(deck, Suit.SPADES));
		
		//Four of each number
		assertEquals(4, getNumCards(deck,"A"));
		assertEquals(4, getNumCards(deck,"2"));
		assertEquals(4, getNumCards(deck,"3"));
		assertEquals(4, getNumCards(deck,"4"));
		assertEquals(4, getNumCards(deck,"5"));
		assertEquals(4, getNumCards(deck,"6"));
		assertEquals(4, getNumCards(deck,"7"));
		assertEquals(4, getNumCards(deck,"8"));
		assertEquals(4, getNumCards(deck,"9"));
		assertEquals(4, getNumCards(deck,"10"));
		assertEquals(4, getNumCards(deck,"J"));
		assertEquals(4, getNumCards(deck,"Q"));
		assertEquals(4, getNumCards(deck,"K"));
	}
	
	private int getNumCards(ArrayList<Card> deck, Suit suit) {
		int count = 0;
		for (Card card : deck) {
			if (card.getSuit() == suit) count++;
		}
		return count;
	}
	
	private int getNumCards(ArrayList<Card> deck, String val) {
		int count = 0;
		for (Card card : deck) {
			if (card.getValue().equals(val)) count++;
		}
		return count;
	}
}
