package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.api.*;
import davidgoldstein.blackjack.model.*;
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


@RunWith(SpringRunner.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DataMongoTest
public class GameServiceTests {

    @Autowired
    MongoGameStateRepository repo;
    GameService service;
    private static final String gameID = "testId1";
    private static final String playerName = "david";

    @BeforeEach
    public void reset() {
        repo.deleteAll();
        service = new GameService(new Repository(repo));
    }

    @Test
    public void checksForUnknownAction() {
        Player p = new Player(playerName);
        Game gs = new Game(
                gameID,
                GameStatus.INIT.toString(),
                new Player[]{p}
        );
        repo.save(gs);
        Exception thrown = null;
        try {
            service.processAction(gameID, new ActionRequest("SDFSDFSDF",p.getId()));
        } catch(IllegalArgumentException e) {
            thrown = e;
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(thrown);
    }

    @Test
    public void processPlayerBet() throws GameNotFoundException {
        Player p =new Player(playerName);
        int initialMoney = 100;
        int betAmount = 5;
        p.setMoney(initialMoney);
        Game gs = new Game(
                gameID,
                GameStatus.WAITING_FOR_BETS.toString(),
                new Player[]{p}
        );
        repo.save(gs);
        ActionRequest ar = new PlaceBetRequest(
                p.getId(),
                betAmount
        );

        Game newGs = service.processAction(gameID, ar);
        Assertions.assertNotNull(newGs);
        Assertions.assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE.toString(), newGs.getStatus());
        // assert that player now has less money
        Assertions.assertEquals(betAmount, newGs.getPlayers()[0].getBet());
        // assert that game pot now has more money
        Assertions.assertEquals(initialMoney - betAmount, newGs.getPlayers()[0].getMoney());
        Assertions.assertEquals(PersonStatus.HAS_BET.toString(), newGs.getPlayers()[0].getStatus());
    }
    @Test
    public void cannotBetIfNotEnoughMoney() throws GameNotFoundException {
        Player p =new Player(playerName);
        int initialMoney = 100;
        int betAmount = 105;
        p.setMoney(initialMoney);
        Game gs = new Game(
                gameID,
                GameStatus.WAITING_FOR_BETS.toString(),
                new Player[]{p}
        );
        repo.save(gs);
        ActionRequest ar = new PlaceBetRequest(
                p.getId(),
                betAmount
        );

        Game newGs = service.processAction(gameID, ar);
        Assertions.assertNotNull(newGs);
        Assertions.assertEquals(GameStatus.WAITING_FOR_BETS.toString(), newGs.getStatus());
        // assert that no money taken from player
        Assertions.assertEquals(0, gs.getPlayer(p.getId()).getBet());
        // assert that pot is the same
        Assertions.assertEquals(initialMoney, newGs.getPlayers()[0].getMoney());
    }

    @Test
    public void willNotDealCardsIfAllPlayersHaveNotBet() throws GameNotFoundException {
        Player p0 =new Player(playerName);
        Player p1 =new Player(playerName + "2");
        int initialMoney = 100;
        int betAmount = 5;
        p0.setMoney(initialMoney);
        p1.setMoney(initialMoney);
        Game gs = new Game(
                gameID,
                GameStatus.WAITING_FOR_BETS.toString(),
                new Player[]{p0,p1}
        );
        repo.save(gs);
        ActionRequest ar = new PlaceBetRequest(
                p0.getId(),
                betAmount
        );

        Game newGs = service.processAction(gameID, ar);
        Assertions.assertNotNull(newGs);
        Assertions.assertEquals(GameStatus.WAITING_FOR_BETS.toString(), newGs.getStatus());
        // assert that player now has less money
        Assertions.assertEquals(betAmount, newGs.getPlayers()[0].getBet());
        Assertions.assertEquals(initialMoney - betAmount, newGs.getPlayers()[0].getMoney());
    }
}
