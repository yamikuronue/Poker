package mocks;

import pokerServer.Card;
import pokerServer.Game;
import pokerServer.Lobby;
import pokerServer.Message;
import pokerServer.Player;
import pokerServer.StateMessage;

public class mockPlayer extends Player {

	public int numCardsDealt = 0;
	public Message lastStateMessage = null;
	
	public mockPlayer() {
		super("user", "user@example.com", new mockClient(), 100);
	}
	
	public mockPlayer(String username) {
		super(username, "user@example.com", new mockClient(), 100);
	}

	@Override
	public void addCardToHand(Card card) {
		numCardsDealt++;
	}

	@Override
	public void resetHand() {
		//do nothing
	}


	@Override
	public void onMessageReceived(Message message) {
		//do nothing
	}

	@Override
	public void onStateChanged(StateMessage newState) {
		lastStateMessage = newState;
	}

	@Override
	public boolean joinGame(Game gameToJoin) {
		return false;
	}

	@Override
	public boolean joinLobby(Lobby lobbyToJoin) {
		return false;
	}
	
	

}
