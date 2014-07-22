package pokerServer;

import java.util.ArrayList;

import pokerServer.Card.Suit;

public class Card {

	private Suit mySuit;
	private String myValue;
	
	public static enum Suit {
		HEARTS,
		CLUBS,
		SPADES,
		DIAMONDS
	}
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

	public Card(Suit suit, String val) {
		this.mySuit = suit;
		
		//Test value to ensure it's valid
		try {
			Integer intval = Integer.parseInt(val);
			if (intval < 1 || intval > 10) {
				throw new IllegalArgumentException("Invalid value: must be one of 1-10, A, Q, J, or K");
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



	public Suit getSuit() {
		return mySuit;
	}

	public String getValue() {
		return myValue;
	}
}
