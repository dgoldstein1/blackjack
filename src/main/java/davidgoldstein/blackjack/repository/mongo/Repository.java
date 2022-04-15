package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * MongoDB implementation of a game repository
 */
@Service
public class Repository implements GameRepository {
    private MongoGameStateRepository repo;

    @Autowired
    public Repository(MongoGameStateRepository repo) {
        this.repo = repo;
    }

    @Override
    public Game retrieveById(String gameId) throws GameNotFoundException {
        Optional<Game> gs = repo.findById(gameId);
        if (gs.isEmpty()) {
            throw new GameNotFoundException("game does not exist: " + gameId);
        }
        return gs.get();
    }

    public Game createGame(Game gs) throws GameAlreadyExistsException {
        boolean gameAlreadyExists = true;
        try {
            retrieveById(gs.getId());
        } catch (GameNotFoundException e) {
            gameAlreadyExists = false;
        }
        if (gameAlreadyExists) {
            throw new GameAlreadyExistsException("game already exists: " + gs.getId());
        }
        // else insert into repo
        return repo.save(gs);
    }

    @Override
    public Game updateExisting(Game gs) throws GameNotFoundException {
        // assert that game exists first
        retrieveById(gs.getId());
        return repo.save(gs);
    }


    /**
     * updates the game state of an existing game
     * @param gameId
     * @param gs
     * @return
     * @throws GameNotFoundException
     */
    @Override
    public Game setGameState(String gameId, Game gs) throws GameNotFoundException, IllegalArgumentException  {
        if (gameId != gs.getId())
            throw new IllegalArgumentException("cannot change game id");
        // assert that game exists first
        retrieveById(gameId);
        return repo.save(gs);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public List<Game> listGames() {
        return repo.findAll();
    }
}


