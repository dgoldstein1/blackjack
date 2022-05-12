package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameTest {

    @Test
    public void hitSplitPlayer() throws DeckIsEmptyException {
        Player p = new Player("testID");
        p.setStatus(PersonStatus.HAS_BET.toString());
        p.setHands(new Hand[]{
                new Hand(new Card(Suit.CLUBS, CardType.TEN, 10)),
                new Hand(new Card(Suit.SPADES, CardType.TEN, 10))
        });
        p.setHasSplit();
        Game g = new Game(
                "test",
                GameStatus.STARTED.toString(),
                new Player[]{p}
        );
        g.hitPlayer(p.getId(), 1);
        Assert.assertEquals(2, p.getAllHands()[1].size());
        Assert.assertEquals(1, p.getAllHands()[0].size());
        Assert.assertEquals(PersonStatus.HAS_BET.toString(), p.getStatus());
    }

    @Test
    public void hitSplitPlayerDoesNotBust() throws DeckIsEmptyException {
        Player p = new Player("testID");
        p.setStatus(PersonStatus.HAS_BET.toString());
        ArrayList<Card> h1 = new ArrayList<>();
        h1.add(new Card(Suit.CLUBS, CardType.ACE, 1));
        h1.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        h1.add(new Card(Suit.HEARTS, CardType.TEN, 10));
        p.setHands(new Hand[]{
                new Hand(h1),
                new Hand(new Card(Suit.SPADES, CardType.TEN, 10))
        });
        p.setHasSplit();
        Game g = new Game(
                "test",
                GameStatus.STARTED.toString(),
                new Player[]{p}
        );
        g.hitPlayer(p.getId(), 0);
        Assert.assertEquals(4, p.getAllHands()[0].size());
        Assert.assertEquals(1, p.getAllHands()[1].size());
        Assert.assertEquals(PersonStatus.HAS_BET.toString(), p.getStatus());
    }
    @Test
    public void hitSplitPlayerBustsAllOver21() throws DeckIsEmptyException {
        Player p = new Player("testID");
        p.setStatus(PersonStatus.HAS_BET.toString());
        ArrayList<Card> h1 = new ArrayList<>();
        h1.add(new Card(Suit.CLUBS, CardType.SIX, 6));
        h1.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        h1.add(new Card(Suit.HEARTS, CardType.TEN, 10));
        ArrayList<Card> h2 = new ArrayList<>();
        h2.add(new Card(Suit.SPADES, CardType.SIX, 6));
        h2.add(new Card(Suit.DIAMONDS, CardType.TEN, 10));
        h2.add(new Card(Suit.SPADES, CardType.TEN, 10));
        p.setHands(new Hand[]{
                new Hand(h1),
                new Hand(h2)
        });
        p.setHasSplit();
        Game g = new Game(
                "test",
                GameStatus.STARTED.toString(),
                new Player[]{p}
        );
        g.hitPlayer(p.getId(), 1);
        Assert.assertEquals(3, p.getAllHands()[0].size());
        Assert.assertEquals(4, p.getAllHands()[1].size());
        Assert.assertEquals(PersonStatus.BUSTED.toString(), p.getStatus());
    }
    @Test
    public void endFromSplit() throws DeckIsEmptyException {
        Player p = new Player("testID");
        p.setBet(5);
        p.setMoney(100);
        p.setStatus(PersonStatus.STOOD.toString());
        ArrayList<Card> h1 = new ArrayList<>();
        h1.add(new Card(Suit.CLUBS, CardType.ACE, 1));
        h1.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        h1.add(new Card(Suit.HEARTS, CardType.TEN, 10));
        p.setHands(new Hand[]{
                new Hand(new Card(Suit.SPADES, CardType.TEN, 10)),
                new Hand(h1),
        });
        p.setHasSplit();
        Game g = new Game(
                "test",
                GameStatus.STARTED.toString(),
                new Player[]{p}
        );
        g.getDealer().setPrimaryHand(new Hand(new Card(Suit.DIAMONDS, CardType.NINE, 9)));
        g.end();
        if (p.getMoney() == 95) {
            Assert.assertEquals(21, g.getDealer().getPrimaryHand().getMaxPointsLT21());
        } else {
            Assert.assertEquals(105, p.getMoney());
        }
    }
}
