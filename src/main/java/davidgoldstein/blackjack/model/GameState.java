package davidgoldstein.blackjack.model;

/**
 * any-time something "happens" in the game
 * @author Z008HBS
 *
 */
public enum GameState {
    STARTED("started"),
    WAITING_FOR_PLAYER_TO_PLAY_CARD("waiting for player to playcard"),
    WAITING_FOR_BET("waiting for bet"),
    ENDED("ended"),
    UNKNOWN("unknown");

    private final String value;

    GameState(String val) {
        this.value = val;
    }

    public static GameState fromValue(String value) {
        if (value != null) {
            for (GameState ac : values()) {
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