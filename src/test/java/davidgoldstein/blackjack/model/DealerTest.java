package davidgoldstein.blackjack.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DealerTest {

    @Test
    public void standIf17OrMore() {
        Dealer d = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(Suit.CLUBS, CardType.SEVEN, 7));
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        d.setHand(hand);
        d.finishHand();
        assertEquals(PersonStatus.STOOD.toString(), d.getStatus());
        assertEquals(hand, d.getHand());
    }

    @Test
    public void takesACardIf16orUnder() {
        Dealer d = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(Suit.CLUBS, CardType.SEVEN, 6));
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        d.setHand(hand);
        d.finishHand();
        assertTrue(d.getHand().size() > 2);
    }

    @Test
    // dealer stands if having ace is greater / equal to 17
    public void standsIfAceIsOver17() {
        Dealer d = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(Suit.CLUBS, CardType.ACE, 11));
        hand.add(new Card(Suit.CLUBS, CardType.SIX, 6));
        d.setHand(hand);
        d.finishHand();
        assertEquals(PersonStatus.STOOD.toString(), d.getStatus());
        assertTrue(d.getHand().size() == 2);
    }

    @Test
    // dealer takes card if having ace puts over 17
    public void takesCardIfAceIsOver21() {
        Dealer d = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(Suit.CLUBS, CardType.ACE, 11));
        hand.add(new Card(Suit.HEARTS, CardType.ACE, 11));
        d.setHand(hand);
        d.finishHand();
        assertTrue(d.getHand().size() > 2);
    }

}
