package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.GameState;
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
        GameState gs = new GameState(
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
        Optional<GameState> gs = repo.findById(gameID);
        Assertions.assertTrue(gs.isPresent());
    }

    @Test
    public void createsValidGameStructure() {
        Optional<GameState> gs = repo.findById(gameID);
        Assertions.assertTrue(gs.isPresent());
        GameState gameState = gs.get();
        Assertions.assertEquals(gameState.getStatus(), GameStatus.INIT.toString());
        Assertions.assertEquals(1, gameState.getPlayers().length);
        Assertions.assertEquals(playerName1, gameState.getPlayers()[0].getName());
    }

}
