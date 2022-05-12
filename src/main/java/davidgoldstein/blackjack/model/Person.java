package davidgoldstein.blackjack.model;

/**
 * dealer and players are people
 * @author Z008HBS
 *
 */
public interface Person {
	public Hand getPrimaryHand();
	public void setPrimaryHand(Hand hand);
	public Hand discardAllCards();
	public String getName();
	public void reset();
}
