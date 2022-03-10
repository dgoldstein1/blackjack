/**
 * 
 */
package com.davidgoldstein.blackjack.model;


/**
 * @author Z008HBS
 * Deck of playing cards
 */
public class Deck {
	Card[] deck;
	Card[] discardPile;
	
	/**
	 * Initialize new deck
	 */
	public Deck() {
		discardPile = new Card[52];
		deck = new Card[52];
		
		// TODO: initialize cards correctly in deck
	}
	
	/**
	 * returns the next 
	 * @param count
	 * @return
	 */
	public Card[] NextCards(int count) throws DeckIsEmptyException {
		// TODO: implement
		return this.deck;
	}
	
	/**
	 * shuffles deck into random order
	 */
	public void Shuffle() {
		
	}
	
	/**
	 * adds all cards from discard pile back into main deck
	 * shuffles the deck into new order
	 */
	public void Reset() {
		
	}
}
