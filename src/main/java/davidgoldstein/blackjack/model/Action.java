package davidgoldstein.blackjack.model;

import org.squirrelframework.foundation.event.SquirrelEvent;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum Action {
	// dealer actions
	DEAL_CARDS("deal card"),
	COLLECT_BETS("collect bets"),
	// player actions
	START_GAME("start game"),
	PLACE_BET("place bet"),
	HIT_ME("hit me"),
	STAND("stand"),
	UNKNOWN("unknown");

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
