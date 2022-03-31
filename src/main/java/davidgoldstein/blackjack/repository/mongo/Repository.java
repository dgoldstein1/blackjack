package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GameState retrieveById(String gameId) throws GameNotFoundException {
        Optional<GameState> gs = repo.findById(gameId);
        if (gs.isEmpty()) {
            throw new GameNotFoundException("game does not exist: " + gameId);
        }
        return gs.get();
    }

    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        boolean gameAlreadyExists = true;
        try {
            retrieveById(gameId);
        } catch (GameNotFoundException e) {
            gameAlreadyExists = false;
        }
        if (gameAlreadyExists) {
            throw new GameAlreadyExistsException("game already exists: " + gameId);
        }
        // else insert into repo
        return repo.save(new GameState(gameId));
    }

    @Override
    public GameState setGameState(String gameId, GameState gs) throws GameNotFoundException {
        // TODO
        return null;
    }
}


