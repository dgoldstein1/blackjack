package davidgoldstein.blackjack.repository;

public class GameNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public GameNotFoundException(String msg) {
        super(msg);
    }
}
