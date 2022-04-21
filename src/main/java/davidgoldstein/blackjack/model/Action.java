package davidgoldstein.blackjack.model;

import org.squirrelframework.foundation.event.SquirrelEvent;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum Action {
	// dealer actions
	DEAL_CARDS("DEAL_CARDS"),
	COLLECT_BETS("COLLECT_BETS"),
	// player actions
	START_GAME("START_GAME"),
	PLACE_BET("PLACE_BET"),
	HIT_ME("HIT_ME"),
	STAND("STAND"),
	DOUBLE("DOUBLE"),
	// internal (not to be called by players
	INTERNAL_FINISH_BETS("INTERNAL_FINISH_BETS"),
	INTERNAL_END_GAME("INTERNAL_END_GAME"),
	UNKNOWN("UNKNOWN");

	private final String value;
	Action(String val) {
		this.value = val;
	}

	public static Action fromString(String value) {
		if (value != null) {
			for (Action ac : values()) {
				if (ac.value.equals(value)) {
					return ac;
				}
			}
		}
		return UNKNOWN;
	}

	public String toString() {
		return value;
	}
}
