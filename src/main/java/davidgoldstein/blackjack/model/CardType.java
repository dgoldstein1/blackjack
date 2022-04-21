package davidgoldstein.blackjack.model;

/**
 * name value of cards in a deck
 * @author Z008HBS
 */
public enum CardType {
	ACE("ACE"),
	QUEEN("QUEEN"),
	KING("KING"),
	JACK("JACK"),
	TEN("TEN"),
	NINE("NINE"),
	EIGHT("EIGHT"),
	SEVEN("SEVEN"),
	SIX("SIX"),
	FIVE("FIVE"),
	FOUR("FOUR"),
	THREE("THREE"),
	TWO("TWO"),
	UNKNOWN("UNKNOWN");

	private final String value;
	CardType(String val) {
		this.value = val;
	}

	public static CardType fromString(String value) {
		if (value != null) {
			for (CardType ac : values()) {
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