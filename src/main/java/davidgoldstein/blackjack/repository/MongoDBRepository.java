package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * MongoDB implementation of a game repository
 */
@Service
public class MongoDBRepository implements GameRepository{

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


