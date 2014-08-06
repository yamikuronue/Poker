package pokerServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import pokerServer.interfaces.Client;
import pokerServer.interfaces.ClientObserver;
import pokerServer.interfaces.StateObserver;

/**
 * Instances of the class “Player” shall represent a single player, either in a game or in the lobby. 
 * @author bgreen
 */
public class Player implements StateObserver, ClientObserver {
	private String username;
	private URL avatarURL;
	private Integer chipsRemaining;
	private ArrayList<Card> currentHand;
	private Game currentGame;
	private Lobby currentLobby;
	private Client client;

	
	/**
	 * Creates a new Player instance
	 * @param username The username to use for this player
	 * @param email The player's email address, for avatar calculation
	 * @param client The client to use to send messages
	 * @param numChips The number of chips, retrieved from the database
	 */
	public Player(String username, String email, Client client, int numChips) {
		super();
		this.username = username;
		this.client = client;
		this.chipsRemaining = numChips;
		
		this.avatarURL = makeGravatarURL(email);
		this.currentGame = null;
		this.currentLobby = null;
		this.currentHand = new ArrayList<Card>();
	}

	private URL makeGravatarURL(String email) {
		//Docs from https://en.gravatar.com/site/implement/hash/
		//1. Trim leading and trailing whitespace from an email address
		email = email.trim();
		
		//2. Force all characters to lower-case
		email = email.toLowerCase();
		
		//3. md5 hash the final string
		MessageDigest md;
		String hashedString = ""; 
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(email.getBytes(Charset.forName("UTF8")));
			
			//convert to hex digits
			StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<thedigest.length; i++) {
	            hexString.append(Integer.toHexString(0xFF & thedigest[i]));
	        }
	        
			hashedString = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Log this once a logger is implemented
			e.printStackTrace();
			hashedString = "00000000000000000000000000000000";
		}
		
		try {
			return new URL("http://www.gravatar.com/avatar/" + hashedString);
		} catch (MalformedURLException e) {
			// TODO Log this once a logger is implemented
			e.printStackTrace();
			try {
				//Hopefully it's an issue with hashedString. Try the backup plan
				return new URL("http://www.gravatar.com/avatar/00000000000000000000000000000000");
			} catch (MalformedURLException e1) {
				// TODO Log this once a logger is implemented
				return null; //We can't save you at this point.
			}
		}
	}
	
	/**
	 * Add a card to the Player's hand. Should be called when dealing.  
	 * @param card The card to add.
	 */	
	public void addCardToHand(Card card) {
		currentHand.add(card);
	}
	
	/**
	 * Reset the hand. Should be called between games.
	 */
	public void resetHand() {
		currentHand = new ArrayList<Card>();
	}
	
	/**
	 * Gets the player's hand, for verification and win validation purposes.
	 * @return The player's current hand.
	 */
	public ArrayList<Card> getHand() {
		return currentHand;
	}
	
	/**
	 * Get the avatar URL for this player
	 * @return The URL
	 */
	public URL getAvatarURL() {
		return avatarURL;
	}
	
	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public void onStateChanged(StateMessage newState) {
		//TODO: Add player's hand state to message
		
		//Then send updated message
		client.sendMessage(newState);
	}

}
