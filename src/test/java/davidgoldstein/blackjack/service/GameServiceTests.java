package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.model.*;
import davidgoldstein.blackjack.repository.GameRepository;
import davidgoldstein.blackjack.repository.mongo.MongoGameStateRepository;
import davidgoldstein.blackjack.repository.mongo.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DataMongoTest
public class GameServiceTests {

    @Autowired
    MongoGameStateRepository repo;
    GameService service;
    private static final String gameID = "testId1";
    private static final UUID playerId = UUID.randomUUID();

    @BeforeEach
    public void reset() {
        repo.deleteAll();
        service = new GameService(new Repository(repo));
    }

    @Test
    public void checksForUnknownAction() {
        GameState gs = new GameState(
                gameID,
                GameStatus.INIT.toString(),
                new Player[]{
                        new Player(playerId.toString())
                }
        );
        repo.save(gs);
        Exception thrown = null;
        try {
            service.processAction(gameID, new ActionRequest("SDFSDFSDF", playerId));
        } catch(IllegalArgumentException e) {
            thrown = e;
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(thrown);
    }

    @Test
    public void processPlayerBet() throws GameNotFoundException {
        Player p =new Player(playerId.toString());
        int initialMoney = 100;
        int betAmount = 5;
        p.setMoney(initialMoney);
        GameState gs = new GameState(
                gameID,
                GameStatus.WAITING_FOR_BETS.toString(),
                new Player[]{p}
        );
        repo.save(gs);
        ActionRequest ar = new PlaceBetRequest(
                playerId,
                betAmount
        );

        GameState newGs = service.processAction(gameID, ar);
        Assertions.assertNotNull(newGs);
        Assertions.assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE.toString(), newGs.getStatus());
        // assert that player now has less money
        Assertions.assertEquals(betAmount, newGs.getPot());
        // assert that game pot now has more money
        Assertions.assertEquals(initialMoney - betAmount, newGs.getPlayers()[0].getMoney());
    }
}
