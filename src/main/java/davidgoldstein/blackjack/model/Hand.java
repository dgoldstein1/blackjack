package davidgoldstein.blackjack.model;

import java.util.ArrayList;

public class Hand  {
    private ArrayList<Card> cards;
    public Hand() {
        cards = new ArrayList<>();
    }
    // initialize hand with specific cards
    public Hand(ArrayList<Card> cards) {
        super();
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

    public int size() {
        return cards.size();
    }

    // todo: max / min points
    public int maxPoints() {
        return cards.stream().reduce(0, (acc, el) -> acc + el.getPointValue(), Integer::sum);
    }
}
