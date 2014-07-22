package pokerServer.interfaces;

import pokerServer.StateMessage;

/**
 * This represents something that observes the state of a game or lobby.
 * It defines a common callback to be used which takes as an argument the Message that represents the new state.
 * @author bgreen
 *
 */
public interface StateObserver extends Observer {
	/**
	 * The callback to use when a state has changed
	 * @param newState The new state
	 */
	public void onStateChanged(StateMessage newState);
}
