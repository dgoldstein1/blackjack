package davidgoldstein.blackjack.model;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum Action {
	PLACE_BET("place bet"),
	HIT_ME("hit me"),
	STAND("stand"),
	UNKNOWN("unknown");

	private final String value;
	Action(String val) {
		this.value = val;
	}

	public static Action fromValue(String value) {
		if (value != null) {
			for (Action ac : values()) {
				if (ac.value.equals(value)) {
					return ac;
				}
			}
		}
		return UNKNOWN;
	}

	public String toValue() {
		return value;
	}
}
