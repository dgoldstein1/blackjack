package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoDBRepositoryTests {

    @Test
    void CreateNewGame() throws GameAlreadyExistsException {
        MongoDBRepository gs = new MongoDBRepository();
        GameState gameState = gs.createGame("test");
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        MongoDBRepository gs = new MongoDBRepository();
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

