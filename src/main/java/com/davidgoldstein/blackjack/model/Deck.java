/**
 * 
 */
package com.davidgoldstein.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author Z008HBS
 * Deck of playing cards
 */
public class Deck {
	Stack<Card> deck;
	Stack<Card> discardPile;
	// size of deck when full
	int maxSize;
	int size;
	
	/**
	 * Initialize new deck
	 */
	public Deck() {
		init();
	}
	
	private void init() {
		this.discardPile = new Stack<Card>();
		this.deck = initialDeck();
		this.maxSize = this.deck.size();
		this.size = this.deck.size();
	}
	
	/**
	 * returns the next 
	 * @param count
	 * @return
	 */
	public List<Card> draw(int count) throws DeckIsEmptyException {
		// check if there are enough cards in deck
		if (count > this.deck.size()) {
			throw new DeckIsEmptyException(String.format("requested %d cards but only %d available", count, this.deck.size()));
		}
		// remove from deck
		ArrayList<Card> cards = new ArrayList<Card>();		
		for(int i = 0; i < count; i++) {
			cards.add(this.deck.pop());			
		}
		return cards;
	}
	
	/**
	 * shuffles deck into random order
	 */
	public void shuffle() {
		Collections.shuffle(this.deck);
	}
	
	/**
	 * adds all cards from discard pile back into main deck
	 * shuffles the deck into new order
	 */
	public void reset() {
		init();
		this.shuffle();
	}
	
	/**
	 * @return max size
	 */
	public int getMaxSize() {
		return this.maxSize;
	}
	
	/**
	 * @return number of cards currently in deck
	 */
	public int getSize() {
		return this.deck.size();
	}
	
	/**
	 * initializes classic 52 card deck in order
	 * @return Card[] in order
	 */
	private Stack<Card> initialDeck() {
		Card[] cards = new Card[] {
				new Card(Suit.CLUBS, CardType.TWO, 2),
				new Card(Suit.CLUBS, CardType.THREE, 3),
				new Card(Suit.CLUBS, CardType.FOUR, 4),
				new Card(Suit.CLUBS, CardType.FIVE, 5),
				new Card(Suit.CLUBS, CardType.SIX, 6),
				new Card(Suit.CLUBS, CardType.SEVEN, 7),
				new Card(Suit.CLUBS, CardType.EIGHT, 8),
				new Card(Suit.CLUBS, CardType.NINE, 9),
				new Card(Suit.CLUBS, CardType.TEN, 10),
				new Card(Suit.CLUBS, CardType.JACK, 10),
				new Card(Suit.CLUBS, CardType.QUEEN, 10),
				new Card(Suit.CLUBS, CardType.KING, 10),
				new Card(Suit.CLUBS, CardType.ACE, 1),
				new Card(Suit.HEARTS, CardType.TWO, 2),
				new Card(Suit.HEARTS, CardType.THREE, 3),
				new Card(Suit.HEARTS, CardType.FOUR, 4),
				new Card(Suit.HEARTS, CardType.FIVE, 5),
				new Card(Suit.HEARTS, CardType.SIX, 6),
				new Card(Suit.HEARTS, CardType.SEVEN, 7),
				new Card(Suit.HEARTS, CardType.EIGHT, 8),
				new Card(Suit.HEARTS, CardType.NINE, 9),
				new Card(Suit.HEARTS, CardType.TEN, 10),
				new Card(Suit.HEARTS, CardType.JACK, 10),
				new Card(Suit.HEARTS, CardType.QUEEN, 10),
				new Card(Suit.HEARTS, CardType.KING, 10),
				new Card(Suit.HEARTS, CardType.ACE, 1),
				new Card(Suit.DIAMONDS, CardType.TWO, 2),
				new Card(Suit.DIAMONDS, CardType.THREE, 3),
				new Card(Suit.DIAMONDS, CardType.FOUR, 4),
				new Card(Suit.DIAMONDS, CardType.FIVE, 5),
				new Card(Suit.DIAMONDS, CardType.SIX, 6),
				new Card(Suit.DIAMONDS, CardType.SEVEN, 7),
				new Card(Suit.DIAMONDS, CardType.EIGHT, 8),
				new Card(Suit.DIAMONDS, CardType.NINE, 9),
				new Card(Suit.DIAMONDS, CardType.TEN, 10),
				new Card(Suit.DIAMONDS, CardType.JACK, 10),
				new Card(Suit.DIAMONDS, CardType.QUEEN, 10),
				new Card(Suit.DIAMONDS, CardType.KING, 10),
				new Card(Suit.DIAMONDS, CardType.ACE, 1),
				new Card(Suit.SPADES, CardType.TWO, 2),
				new Card(Suit.SPADES, CardType.THREE, 3),
				new Card(Suit.SPADES, CardType.FOUR, 4),
				new Card(Suit.SPADES, CardType.FIVE, 5),
				new Card(Suit.SPADES, CardType.SIX, 6),
				new Card(Suit.SPADES, CardType.SEVEN, 7),
				new Card(Suit.SPADES, CardType.EIGHT, 8),
				new Card(Suit.SPADES, CardType.NINE, 9),
				new Card(Suit.SPADES, CardType.TEN, 10),
				new Card(Suit.SPADES, CardType.JACK, 10),
				new Card(Suit.SPADES, CardType.QUEEN, 10),
				new Card(Suit.SPADES, CardType.KING, 10),
				new Card(Suit.SPADES, CardType.ACE, 1),
		};
		Stack<Card> d = new Stack<Card>();
		for(int i = 0; i < cards.length; i++) {
			d.push(cards[i]);
		}
		return d;
	}
}
