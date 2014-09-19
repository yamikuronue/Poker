package mocks;

import pokerServer.Card;
import pokerServer.Game;
import pokerServer.Lobby;
import pokerServer.Message;
import pokerServer.Player;
import pokerServer.StateMessage;

public class mockPlayer extends Player {

	public mockPlayer() {
		super("user", "user@example.com", new mockClient(), 100);
	}

	@Override
	public void addCardToHand(Card card) {
		//do nothing
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
		//do nothing
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
