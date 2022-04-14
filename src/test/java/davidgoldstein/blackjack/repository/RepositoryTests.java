package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.repository.mongo.MongoGameStateRepository;
import davidgoldstein.blackjack.repository.mongo.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class RepositoryTests {

    @Autowired
    MongoGameStateRepository repo;

    @BeforeEach
    public void resetDb() {
        repo.deleteAll();
    }

    @Test
    void CreateNewGame() throws GameAlreadyExistsException {
        Repository gs = new Repository(repo);
        GameState gameState = gs.createGame(new GameState("test"));
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        Repository gs = new Repository(repo);
        gs.createGame(new GameState("test"));
        Exception exception = null;
        try {
            gs.createGame(new GameState("test"));
        } catch (GameAlreadyExistsException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals("game already exists: test", exception.getMessage());
    }

    @Test
    void setGameState() throws GameNotFoundException {
        GameState gameState = new GameState("test");
        repo.save(gameState);
        Repository gs = new Repository(repo);
        gameState.setName("new name");
        gameState = gs.setGameState("test",gameState);
        assertNotNull(gameState);
        assertEquals("test", gameState.getId());
        assertEquals("new name", gameState.getName());
    }
}

