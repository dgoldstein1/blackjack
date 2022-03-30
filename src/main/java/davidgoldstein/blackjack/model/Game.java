package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
	public void HandleGameEvent(ActionRequest e) throws InvalidMoveException {
		throw new InvalidMoveException(String.format("no such action :%s", e.getAction()));
	}
}
