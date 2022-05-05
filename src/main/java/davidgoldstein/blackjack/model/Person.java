package davidgoldstein.blackjack.model;

import java.util.ArrayList;

/**
 * dealer and players are people
 * @author Z008HBS
 *
 */
public interface Person {
	public Hand getHand();
	public void setHand(Hand hand);
	public Hand discardHand();
	public String getName();
}
