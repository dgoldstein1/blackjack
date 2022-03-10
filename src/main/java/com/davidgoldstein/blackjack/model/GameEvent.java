package com.davidgoldstein.blackjack.model;

/**
 * structure for holding on to game events
 * @author Z008HBS
 *
 */
public class GameEvent {
	Person initiator;
	PlayerEvent type;
	
	public GameEvent(Person p, PlayerEvent et) {
		this.initiator = p;
		this.type = et;
	}
	
	public String toString() {
		return String.format("initiator: %s, type : %s", this.initiator, this.type);
	}
}
