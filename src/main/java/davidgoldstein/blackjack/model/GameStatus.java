package davidgoldstein.blackjack.model;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum GameStatus {
    INIT("initialized"),
    STARTED("started"),
    WAITING_FOR_CARD("waiting for player to play card"),
    DEALING_CARDS("dealing cards"),
    WAITING_FOR_BET("waiting for bet"),
    ENDED("ended"),
    UNKNOWN("unknown");

    private final String value;

    GameStatus(String val) {
        this.value = val;
    }

    public static GameStatus fromValue(String value) {
        if (value != null) {
            for (GameStatus ac : values()) {
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