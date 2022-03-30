package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.beans.ActionRequest;

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
	public void HandleGameEvent(ActionRequest e) throws InvalidMoveException {
		throw new InvalidMoveException(String.format("no such action :%s", e.getAction()));
	}
	
}
