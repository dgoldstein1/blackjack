package com.davidgoldstein.blackjack.model;

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
	public void HandleGameEvent(GameEvent e) throws InvalidMoveException {
		switch (e.type) {
		case 
		default:
			throw new InvalidMoveException(String.format("no such move: %s", e.type));
			break;
		
		}
	}
	
}
