package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dealer implements Person, Serializable {
	ArrayList<Card> hand;
	Deck deck;
	
	public Dealer() {
		this.hand = new ArrayList<Card>();
		this.deck = new Deck();
		this.deck.shuffle();
	}

	/**
	 * @return one card that is face up and one tha is face down
	 * @throws DeckIsEmptyException
	 */
	public ArrayList<Card> dealHand() throws DeckIsEmptyException {
		ArrayList<Card> hand = deck.draw(2);
		hand.get(0).setFaceDown();
		return hand;
	}

	// put cards in discard pile
	public void discard(ArrayList<Card> cards) {
		cards.forEach(c -> deck.discard(c));
	}

	public void setHand(ArrayList<Card> hand) {this.hand = hand;}

	@Override
	public ArrayList<Card> discardHand() {
		ArrayList<Card> currHand = hand;
		hand = new ArrayList<Card>();
		return currHand;
	}

	@Override
	public ArrayList<Card> getHand() {
		return this.hand;
	}

	@Override
	public String getName() {
		return "Dealer";
	}

}
