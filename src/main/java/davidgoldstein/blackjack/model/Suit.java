package davidgoldstein.blackjack.model;

public enum Suit {
	HEARTS("HEARTS"),
	SPADES("SPADES"),
	CLUBS("CLUBS"),
	DIAMONDS("DIAMONDS"),
	UNKNOWN("UNKNOWN");


	private final String value;
	Suit(String val) {
		this.value = val;
	}

	public static Suit fromString(String value) {
		if (value != null) {
			for (Suit ac : values()) {
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
