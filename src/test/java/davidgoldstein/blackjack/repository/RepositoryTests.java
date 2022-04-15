package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.Game;
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
        Game game = gs.createGame(new Game("test"));
        assertNotNull(game);
        assertEquals("test", game.getId());
    }

    @Test
    void CannotCreateDuplicateGames() throws GameAlreadyExistsException {
        Repository gs = new Repository(repo);
        gs.createGame(new Game("test"));
        Exception exception = null;
        try {
            gs.createGame(new Game("test"));
        } catch (GameAlreadyExistsException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals("game already exists: test", exception.getMessage());
    }

    @Test
    void setGameState() throws GameNotFoundException {
        Game game = new Game("test");
        repo.save(game);
        Repository gs = new Repository(repo);
        game.setName("new name");
        game = gs.setGameState("test", game);
        assertNotNull(game);
        assertEquals("test", game.getId());
        assertEquals("new name", game.getName());
    }
}

