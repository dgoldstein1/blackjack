package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.model.ActionRequest;
import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.machine.GameStateMachineFactory;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import java.util.List;


@Service
public class GameService {
    @Autowired
    GameRepository repo;

    public GameService(GameRepository repo) {
        this.repo = repo;
    }

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
    public GameState processAction(String gameId, ActionRequest req) throws IllegalArgumentException, GameNotFoundException {
        // find game in repo
        GameState curr = repo.retrieveById(gameId);
        GameStatus currStatus = GameStatus.fromString(curr.getStatus());
        // validate action
        if (req.getAction() == Action.UNKNOWN) {
            throw new IllegalArgumentException("no such action: " + req);
        }
        // build internal model
        UntypedStateMachine gsm = GameStateMachineFactory.build(currStatus);
        // apply event to game
        gsm.fire(req.getAction(), curr);
        // check for error
        if (gsm.getLastException() != null ) {
            throw gsm.getLastException();
        }
        curr.setStatus(gsm.getCurrentState().toString());
        // update in repo
        return repo.updateExisting(curr);
    }
}
