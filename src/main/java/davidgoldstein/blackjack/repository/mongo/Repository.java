package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
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
    public GameState retrieveById(String gameId) throws GameNotFoundException {
        Optional<GameState> gs = repo.findById(gameId);
        if (gs.isEmpty()) {
            throw new GameNotFoundException("game does not exist: " + gameId);
        }
        return gs.get();
    }

    public GameState createGame(GameState gs) throws GameAlreadyExistsException {
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
    public GameState updateExisting(GameState gs) throws GameNotFoundException {
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
    public GameState setGameState(String gameId, GameState gs) throws GameNotFoundException, IllegalArgumentException  {
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
    public List<GameState> listGames() {
        return repo.findAll();
    }
}


