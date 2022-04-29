package davidgoldstein.blackjack.model;

public enum PersonStatus {
    INIT("INIT"),
    BUSTED("BUSTED"),
    STOOD("STOOD"),
    HAS_BET("HAS_BET"),
    UNKNOWN("UNKNOWN"),;

    private final String value;

    PersonStatus(String val) {
        this.value = val;
    }

    public static PersonStatus fromString(String value) {
        if (value != null) {
            for (PersonStatus ac : values()) {
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
