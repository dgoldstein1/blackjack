package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.mongo.MongoGameStateRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DataMongoTest
public class MongoGameStateRepositoryTests {

    @Autowired
    MongoGameStateRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.save(new GameState("test"));
    }

    @Test
    public void shouldBeNotEmpty() {
        Optional<GameState> gs = repo.findById("test");
        assertNotNull(gs);
    }
}
