package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTests {

    @Test
    void CreateNewGame() throws GameAlreadyExistsException {
        GameService gs = new GameService();
        GameState gameState = gs.createGame("test");
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        GameService gs = new GameService();
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

