package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.beans.Action;
import davidgoldstein.blackjack.beans.GameState;

public class Game {
	GameState state;

	public Game(String id) {
		//TODO: initialize vars
		state = new GameState(id);
	}

	public GameState getState() {
		return this.state;
	}

	/**
	 * handles incoming input into game
	 * @param e event
	 * @throws InvalidMoveException when move is invalid
	 */
	public void HandleGameEvent(Action e) throws InvalidMoveException {
		throw new InvalidMoveException(String.format("no such action :%s", e.getAction()));
	}
}
