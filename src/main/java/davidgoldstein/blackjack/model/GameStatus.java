package davidgoldstein.blackjack.model;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum GameStatus {
    INIT("INIT"),
    STARTED("STARTED"),
    WAITING_FOR_PLAYER_MOVE("WAITING_FOR_PLAYER_MOVE"),
    DEALING_CARDS("DEALING_CARDS"),
    WAITING_FOR_BETS("WAITING_FOR_BETS"),
    WAITING_FOR_PLAYER("WAITING_FOR_PLAYER"),
    ENDED("ENDED"),
    UNKNOWN("UNKNOWN");

    private final String value;

    GameStatus(String val) {
        this.value = val;
    }

    public static GameStatus fromString(String value) {
        if (value != null) {
            for (GameStatus ac : values()) {
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