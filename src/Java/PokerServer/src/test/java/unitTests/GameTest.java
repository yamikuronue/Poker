/**
 * 
 */
package unitTests;

import static org.junit.Assert.*;
import mocks.mockPlayer;

import org.junit.Test;

import pokerServer.Game;
import pokerServer.Player;
import pokerServer.PokerServer;
import pokerServer.StateMessage;
import pokerServer.interfaces.Observer;
import pokerServer.interfaces.StateObserver;

/**
 * @author bgreen
 *
 */
public class GameTest {

	/**
	 * Observer can be added to a game
	 */
	@Test
	public void observerCanBeAded() {
		RightObserver observer = new RightObserver();
		Game oot = new Game();
		
		assertTrue(oot.addObserver(observer));
	}
	
	/**
	 * Game rejects invalid observer when adding
	 */
	@Test(expected=IllegalArgumentException.class)
	public void invalidObserverRejectedFromAdd() {
		WrongObserver observer = new WrongObserver();
		Game oot = new Game();
		
		oot.addObserver(observer);
	}
	
	/**
	 * Observer can be removed from a game
	 */
	@Test
	public void existingObserverCanBeRemoved() {
		RightObserver observer = new RightObserver();
		Game oot = new Game();
		oot.addObserver(observer);
		
		assertTrue(oot.removeObserver(observer));
	}
	
	/**
	 * Observer cannot be removed from a game if it was not observing
	 */
	@Test
	public void nonExistingObserverCannotBeRemoved() {
		RightObserver observer = new RightObserver();
		Game oot = new Game();
		
		assertFalse(oot.removeObserver(observer));
	}
	

	/**
	 * Game rejects invalid observer when remove is called
	 */
	@Test(expected=IllegalArgumentException.class)
	public void invalidObserverRejectedFromRemote() {
		WrongObserver observer = new WrongObserver();
		Game oot = new Game();
		
		oot.removeObserver(observer);
	}

	/**
	 * Player can be added to a game
	 */
	@Test
	public void playerCanBeAdded() {
		Player p = new mockPlayer();
		Game oot = new Game();
		
		assertTrue(oot.addPlayer(p));
	}
	
	/**
	 * Too many players cannot be added to a game
	 */
	@Test
	public void playerLimitEnforced() {
		Player p1 = new mockPlayer();
		Player p2 = new mockPlayer();
		Game oot = new Game();
		
		PokerServer.MAX_PLAYERS_PER_GAME = 1;
		
		assertTrue(oot.addPlayer(p1));
		assertFalse(oot.addPlayer(p2));
	}
	
	/**
	 * Player can be removed from a game
	 */
	@Test
	public void existingPlayerCanBeRemoved() {
		Player p = new mockPlayer();
		Game oot = new Game();
		oot.addPlayer(p);
		
		assertTrue(oot.removePlayer(p));
	}
	
	/**
	 * Player cannot be removed from a game if it was not observing
	 */
	@Test
	public void nonExistingPlayerCannotBeRemoved() {
		Player p = new mockPlayer();
		Game oot = new Game();
		
		assertFalse(oot.removePlayer(p));
	}
	
	private class WrongObserver implements Observer {
		
	}
	
	private class RightObserver implements StateObserver {

		@Override
		public void onStateChanged(StateMessage newState) {
		
		}
		
	}
}
