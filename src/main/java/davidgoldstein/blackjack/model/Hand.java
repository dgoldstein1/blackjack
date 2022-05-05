package davidgoldstein.blackjack.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    private ArrayList<Card> cards;
    public Hand() {
        cards = new ArrayList<>();
    }
    // initialize hand with specific cards
    public Hand(ArrayList<Card> cards) {
        this();
        this.cards.addAll(cards);
    }

    /**
     * get card in hand
     * @param i index
     * @return card at that location
     */
    public Card get(int i) {
        return cards.get(i);
    }

    public ArrayList<Card> asList() {
        return cards;
    }

    // adds card in order
    public void add(Card c) {
        cards.add(c);
    }
    public void add(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }

    public int size() {
        return cards.size();
    }

    public int minPoints() {
        return cards.stream().reduce(0, (acc, el) -> acc + el.minValue(), Integer::sum);
    }
    public int maxPoints() {
        return cards.stream().reduce(0, (acc, el) -> acc + el.maxValue(), Integer::sum);
    }
    public ArrayList<Card> getCards() {return cards;}
    public void setCards(ArrayList<Card> cards) {this.cards = cards;}

    /**
     * check if hand has specific card type
     * @param type
     * @return true if cardtype is in hand
     */
    public boolean containsType(CardType type) {
        for (Card c: cards) {
            if (c.getCardType().equals(type.toString())) {
                return true;
            }
        }
        return false;
    }
}
