/**
 * 
 */
package davidgoldstein.blackjack.model;

import java.io.Serializable;

/**
 * @author Z008HBS
 * definition of a playing card
 */
public class Card implements Comparable<Card>, Serializable {
	String suit;
	String cardType;
	int pointValue;

	public Card() {
		this.suit = Suit.UNKNOWN.toString();
		this.cardType = CardType.UNKNOWN.toString();
		this.pointValue = -1;
	}

	/**
	 *  constructor
	 * @param s suit
	 * @param ct card type
	 * @param pv point value
	 */
	public Card(String s, String ct, int pv) {
		this.suit = s;
		this.cardType = ct;
		this.pointValue = pv;
	}

	public Card(Suit s, CardType ct, int pv) {
		this(s.toString(), ct.toString(), pv);
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
	public String getSuit() {
		return this.suit;
	}
	
	/**
	 * @return type of card (e.g. QUEEN)
	 */
	public String getCardType() {
		return this.cardType;
	}
	
	public String toString() {
		return String.format("%s of %s", this.cardType, this.suit);
	}

	@Override
	public int compareTo(Card o) {
		return this.toString().compareTo(o.toString());
	}
}