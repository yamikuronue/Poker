package pokerServer;

import java.util.ArrayList;

/**
 * Represents a single playing card. Also contains class methods to deal with decks of cards. 
 * @author bgreen
 *
 */
public class Card {

	private Suit mySuit;
	private String myValue;
	
	/**
	 * The possible suits for a standard deck of French playing cards (the worldwide standard)
	 * @author bgreen
	 *
	 */
	public static enum Suit {
		/** Hearts (♥), one of two red suits*/
		HEARTS,
		
		/**Clubs (♣), one of two black suits*/
		CLUBS,
		
		/**Spades (♠), one of two black suits*/
		SPADES,
		
		/**Diamonds (♦), one of two red suits*/
		DIAMONDS
	}
	
	/**
	 * Generates a fresh, unshuffled deck of playing cards. 
	 * @return The deck, in ArrayList format. 
	 */
	public static ArrayList<Card> generateDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (int i = 2; i <= 10; i++) {
			deck.add(new Card(Suit.HEARTS, Integer.toString(i)));
			deck.add(new Card(Suit.CLUBS, Integer.toString(i)));
			deck.add(new Card(Suit.SPADES, Integer.toString(i)));
			deck.add(new Card(Suit.DIAMONDS, Integer.toString(i)));
		}
		
		//Aces
		deck.add(new Card(Suit.HEARTS, "A"));
		deck.add(new Card(Suit.CLUBS, "A"));
		deck.add(new Card(Suit.SPADES, "A"));
		deck.add(new Card(Suit.DIAMONDS, "A"));
		
		//Queens
		deck.add(new Card(Suit.HEARTS, "Q"));
		deck.add(new Card(Suit.CLUBS, "Q"));
		deck.add(new Card(Suit.SPADES, "Q"));
		deck.add(new Card(Suit.DIAMONDS, "Q"));
		
		//Kings
		deck.add(new Card(Suit.HEARTS, "K"));
		deck.add(new Card(Suit.CLUBS, "K"));
		deck.add(new Card(Suit.SPADES, "K"));
		deck.add(new Card(Suit.DIAMONDS, "K"));
		
		//Jacks
		deck.add(new Card(Suit.HEARTS, "J"));
		deck.add(new Card(Suit.CLUBS, "J"));
		deck.add(new Card(Suit.SPADES, "J"));
		deck.add(new Card(Suit.DIAMONDS, "J"));
		
		return deck;
	}

	/**
	 * Create a new card. A card has both a suit and a value. 
	 * @param suit One of four French suits.
	 * @param val Should either a numeric value between 2 and 10 inclusive, A for Ace, Q for Queen, J for Jack, or K for King
	 */
	public Card(Suit suit, String val) {
		this.mySuit = suit;
		
		//Test value to ensure it's valid
		try {
			Integer intval = Integer.parseInt(val);
			if (intval < 2 || intval > 10) {
				throw new IllegalArgumentException("Invalid value: must be one of 2-10, A, Q, J, or K");
			}
		} catch (NumberFormatException e) {
			if (!val.equalsIgnoreCase("A") 
					&& !val.equalsIgnoreCase("J")
					&& !val.equalsIgnoreCase("Q")
					&& !val.equalsIgnoreCase("K")) {
						throw new IllegalArgumentException("Invalid value: must be one of 1-10, A, Q, J, or K");
					}
		}
		this.myValue = val.toUpperCase(); //if we haven't thrown by now, it's good. Standardize it though.
	}


	/**
	 * Get the suit of this card
	 * @return The suit
	 */
	public Suit getSuit() {
		return mySuit;
	}

	/**
	 * Get the value of this card
	 * @return The value
	 */
	public String getValue() {
		return myValue;
	}

	@Override
	public String toString() {
		String representation = myValue;
		switch(mySuit) {
		case CLUBS:
			representation += "♣";
			break;
		case DIAMONDS:
			representation += "♦";
			break;
		case HEARTS:
			representation += "♥";
			break;
		case SPADES:
			representation += "♠";
			break;
		default:
			break;
		
		}
		
		return representation;
	}
	
	
}
