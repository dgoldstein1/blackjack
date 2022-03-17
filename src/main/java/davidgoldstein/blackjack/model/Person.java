package davidgoldstein.blackjack.model;

import java.util.ArrayList;

/**
 * dealer and players are people
 * @author Z008HBS
 *
 */
public interface Person {
	public ArrayList<Card> getHand();
	public String getName();
}
