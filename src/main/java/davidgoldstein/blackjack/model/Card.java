/**
 * 
 */
package davidgoldstein.blackjack.model;

/**
 * @author Z008HBS
 * definition of a playing card
 */
public class Card implements Comparable<Card>{
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
	
	public String toString() {
		return String.format("%s of %s", this.cardType.name(), this.suit.name());
	}

	@Override
	public int compareTo(Card o) {
		return this.toString().compareTo(o.toString());
	}
}