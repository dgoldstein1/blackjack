package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.api.PlayerEventRequest;

import java.util.ArrayList;

public class Game {
	ArrayList<Player> players;
	Dealer dealer;
	Deck deck;
	
	/**
	 * handles incoming input into game
	 * @param e event
	 * @throws InvalidMoveException when move is invalid
	 */
	public void HandleGameEvent(PlayerEventRequest e) throws InvalidMoveException {
		if (e.type == PlayerEvent.HIT_ME) {
			System.out.printf("HIT_ME: %s",e.initiator);
		}
		throw new InvalidMoveException(String.format("no such event type :%s", e.type));
	}
	
}
