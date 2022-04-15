package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.model.GameStatus;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.repository.mongo.MongoGameStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DataMongoTest
public class MongoGameStatusRepositoryTests {

    @Autowired
    MongoGameStateRepository repo;
    private static final String gameID = "testId1";
    private static final String playerName1 = "David";

    @BeforeEach
    public void setUp() throws Exception {
        repo.deleteAll();
        Game gs = new Game(
                gameID,
                GameStatus.INIT.toString(),
                new Player[]{
                        new Player(playerName1)
                }
        );
        repo.save(gs);
    }

    @Test
    public void shouldBeNotEmpty() {
        Optional<Game> gs = repo.findById(gameID);
        Assertions.assertTrue(gs.isPresent());
    }

    @Test
    public void createsValidGameStructure() {
        Optional<Game> gs = repo.findById(gameID);
        Assertions.assertTrue(gs.isPresent());
        Game game = gs.get();
        Assertions.assertEquals(game.getStatus(), GameStatus.INIT.toString());
        Assertions.assertEquals(1, game.getPlayers().length);
        Assertions.assertEquals(playerName1, game.getPlayers()[0].getName());
    }

}
