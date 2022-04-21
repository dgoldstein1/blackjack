package davidgoldstein.blackjack.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Dealer implements Person, Serializable {
	ArrayList<Card> hand;
	Deck deck;
	
	public Dealer() {
		this.hand = new ArrayList<Card>();
		this.deck = new Deck();
		this.deck.shuffle();
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
