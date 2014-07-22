package pokerServer;

import pokerServer.interfaces.Observable;
import pokerServer.interfaces.Observer;

/**
 * Instances of the class “Game” shall represent a single game currently in progress. 
 * It shall handle the turns of the game, including enforcing the business rules. 
 * There is the possibility of observing a game without being a player in it, though no client currently implements this behavior.
 * @author bgreen
 *
 */
public class Game implements Observable {

	@Override
	public boolean addObserver(Observer observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

	@Override
	public boolean removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Coming soon");
	}

}