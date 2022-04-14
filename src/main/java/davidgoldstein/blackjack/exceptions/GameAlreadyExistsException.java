package davidgoldstein.blackjack.exceptions;

public class GameAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;
    public GameAlreadyExistsException(String msg) {
        super(msg);
    }
}
