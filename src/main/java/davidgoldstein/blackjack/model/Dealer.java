package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;

import java.io.Serializable;
import java.util.ArrayList;

public class Dealer implements Person, Serializable {
	Hand hand;
	Deck deck;
	String status;
	
	public Dealer() {
		this.hand = new Hand();
		this.deck = new Deck();
		this.deck.shuffle();
		this.status = PersonStatus.INIT.toString();
	}

	/**
	 * @return one card that is face up and one tha is face down
	 * @param setFirstFaceDown if true, set the first card in the hand face down
	 * @throws DeckIsEmptyException
	 */
	public Hand dealHand(boolean setFirstFaceDown) throws DeckIsEmptyException {
		Hand hand = new Hand(deck.draw(2));
		if (setFirstFaceDown) {
			hand.get(0).setFaceDown();
		}
		return hand;
	}

	public Card dealCard() throws DeckIsEmptyException {
		return deck.draw(1).get(0);
	}

	/**
	 * If the total is 17 or more, it must stand.
	 * If the total is 16 or under, they must take a card.
	 * The dealer must continue to take cards until the total is 17
	 * 		or more, at which point the dealer must stand.
	 * 		If the dealer has an ace, and counting it as 11 would bring
	 * 		the total to 17 or more (but not over 21), the dealer must
	 * 		count the ace as 11 and stand.
	 */
	public void finishHand() {

	}

	// put cards in discard pile
	public void discard(Hand hand) {
		hand.asList().forEach(c -> deck.discard(c));
	}

	public void setHand(Hand hand) {this.hand = hand;}

	@Override
	public Hand discardHand() {
		Hand currHand = hand;
		hand = new Hand();
		return currHand;
	}

	@Override
	public Hand getHand() {
		return this.hand;
	}

	@Override
	public String getName() {
		return "Dealer";
	}

	public String getStatus() {return this.status;}
	public void setStatus(String status) {this.status = status;}

}
