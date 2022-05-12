package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DealerTest {

    @Test
    public void standIf17OrMore() throws DeckIsEmptyException {
        Dealer d = new Dealer();
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.SEVEN, 7));
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        d.setPrimaryHand(hand);
        d.finishHand();
        assertEquals(PersonStatus.STOOD.toString(), d.getStatus());
        assertEquals(hand, d.getPrimaryHand());
    }

    @Test
    public void takesACardIf16orUnder() throws DeckIsEmptyException {
        Dealer d = new Dealer();
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.SEVEN, 6));
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        d.setPrimaryHand(hand);
        d.finishHand();
        assertTrue(d.getPrimaryHand().size() > 2);
    }

    @Test
    // dealer stands if having ace is greater / equal to 17
    public void standsIfAceIsOver17() throws DeckIsEmptyException {
        Dealer d = new Dealer();
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.ACE, 11));
        hand.add(new Card(Suit.CLUBS, CardType.SIX, 6));
        d.setPrimaryHand(hand);
        d.finishHand();
        assertEquals(PersonStatus.STOOD.toString(), d.getStatus());
        assertTrue(d.getPrimaryHand().size() == 2);
    }

    @Test
    // dealer takes card if having ace puts over 17
    public void takesCardIfAceIsOver21() throws DeckIsEmptyException {
        Dealer d = new Dealer();
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.ACE, 11));
        hand.add(new Card(Suit.HEARTS, CardType.ACE, 11));
        d.setPrimaryHand(hand);
        d.finishHand();
        assertTrue(d.getPrimaryHand().size() > 2);
    }

}
