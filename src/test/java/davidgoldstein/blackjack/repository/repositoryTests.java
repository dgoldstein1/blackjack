package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.mongo.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class repositoryTests {

    @Test
    void CreateNewGame() throws GameAlreadyExistsException {
        Repository gs = new Repository();
        GameState gameState = gs.createGame("test");
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        Repository gs = new Repository();
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

