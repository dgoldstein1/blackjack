package davidgoldstein.blackjack.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DealerTest {

    @Test
    public void standIf17OrMore() {
        Dealer d = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(Suit.CLUBS, CardType.SEVEN, 7));
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        d.setHand(hand);
        d.finishHand();
        assertEquals(PlayerStatus.STOOD.toString(), d.getStatus());
    }

}
