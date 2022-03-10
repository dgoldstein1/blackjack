/**
 * 
 */
package com.davidgoldstein.blackjack.model;

/**
 * @author Z008HBS
 * definition of a playing card
 */
public class Card {
	Suit suit;
	CardType cardType;
	int pointValue;
	
	/**
	 *  constructor
	 * @param s suit
	 * @param ct card type
	 * @param pv point value
	 */
	public Card(Suit s, CardType ct, int pv) {
		this.suit = s;
		this.cardType = ct;
		this.pointValue = pv;
	}
	
	/**
	 * number of points the card is worth
	 * @return value as int
	 */
	public int getPointValue()  {
		return this.pointValue;
	}
	
	/**
	 * @return name of suit (e.g. HEARTS)
	 */
	public Suit getSuit() {
		return this.suit;
	}
	
	/**
	 * @return type of card (e.g. QUEEN)
	 */
	public CardType getCardType() {
		return this.cardType;
	}
}