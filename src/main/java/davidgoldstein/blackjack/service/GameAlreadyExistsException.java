package davidgoldstein.blackjack.service;

public class GameAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;
    GameAlreadyExistsException(String msg) {
        super(msg);
    }
}
