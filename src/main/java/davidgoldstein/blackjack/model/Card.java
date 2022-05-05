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
	// if not face up, then it's face down
	boolean faceUp;

	/**
	 * default constructor
	 */
	public Card() {
		this.suit = Suit.UNKNOWN.toString();
		this.cardType = CardType.UNKNOWN.toString();
		this.pointValue = -1;
		this.faceUp = true;
	}

	/**
	 *  constructor
	 * @param s suit
	 * @param ct card type
	 * @param pv point value
	 */
	public Card(String s, String ct, int pv) {
		this();
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
	public int maxValue()  {
		return this.pointValue;
	}

	/**
	 * max number of points card is worth
	 * @return
	 */
	public int minValue() {
		if (this.cardType.equals(CardType.ACE.toString())) {
			return 1;
		}
		return this.maxValue();
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
	public boolean isFaceUp() { return this.faceUp;}
	public void setFaceUp() {this.faceUp = true;}
	public void setFaceDown() {this.faceUp = false;}

	@Override
	public int compareTo(Card o) {
		return this.toString().compareTo(o.toString());
	}
}