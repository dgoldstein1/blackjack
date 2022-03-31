package davidgoldstein.blackjack.repository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.mongo.Repository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class RepositoryTests {

    @Test
    void CreateNewGame() throws GameAlreadyExistsException {
        Repository gs = new Repository(null);
        GameState gameState = gs.createGame("test");
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        Repository gs = new Repository(null);
        gs.createGame("test");
        Exception exception = null;
        try {
            gs.createGame("test");
        } catch (GameAlreadyExistsException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals("game test already exists", exception.getMessage());
    }
}

