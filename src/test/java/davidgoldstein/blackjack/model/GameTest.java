package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

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
}
