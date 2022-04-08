package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    GameRepository repo;

    /**
     * Retrieve game
     * @param gameId
     * @return
     * @throws GameNotFoundException
     */
    public GameState retrieveById(String gameId) throws GameNotFoundException {
        return repo.retrieveById(gameId);
    }

    /**
     * lists all games in repo
     * @return
     */
    public List<GameState> listGames() {
        return repo.listGames();
    }

    /**
     * creates a new game
     * @param gameId
     * @return
     * @throws GameAlreadyExistsException
     */
    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        return repo.createGame(new GameState(gameId));
    }

    /**
     * processes an incoming action to a game
     * @param gameId game to apply action to
     * @param req request to be made
     * @return new gamestate after action is applied
     */
    public GameState processAction(String gameId, ActionRequest req) throws GameNotFoundException {
        // find game in repo
        GameState curr = repo.retrieveById(gameId);

        // TODO: send event to gamestate machine

        return curr;
    }
}
