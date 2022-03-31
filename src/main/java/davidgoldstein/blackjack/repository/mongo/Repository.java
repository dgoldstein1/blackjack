package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.stereotype.Service;

/**
 * MongoDB implementation of a game repository
 */
@Service
public class Repository implements GameRepository {

    private MongoGameStateRepository repo;

    @Override
    public Game retrieveById(String gameId) {
        //TODO
        return null;
    }

    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        // TODO
        return new GameState();
    }

    @Override
    public GameState setGameState(String gameId, GameState gs) {
        // TODO
        return null;
    }
}


