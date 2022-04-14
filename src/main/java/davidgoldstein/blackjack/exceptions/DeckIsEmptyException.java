package davidgoldstein.blackjack.exceptions;

/**
 * occurs when the deck is empty but an operation was requested 
 * @author Z008HBS
 */
public class DeckIsEmptyException extends Exception {
	private static final long serialVersionUID = 1L;
	public DeckIsEmptyException(String msg) {
		super(msg);
	}

}
