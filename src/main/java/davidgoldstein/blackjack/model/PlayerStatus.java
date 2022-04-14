package davidgoldstein.blackjack.model;

public enum PlayerStatus {
    PLAYING("PLAYING"),
    BUSTED("BUSTED"),
    STOOD("STOOD"),
    UNKNOWN("UNKNOWN");

    private final String value;

    PlayerStatus(String val) {
        this.value = val;
    }

    public static PlayerStatus fromString(String value) {
        if (value != null) {
            for (PlayerStatus ac : values()) {
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
