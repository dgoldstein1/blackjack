package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;

import java.util.List;

/**
 * Interface that all implementations of a repository must implement
 */
public interface GameRepository {
    public void deleteAll();
    public GameState retrieveById(String gameId) throws GameNotFoundException;
    public GameState createGame(GameState gs) throws GameAlreadyExistsException;
    public GameState updateExisting(GameState gs) throws GameNotFoundException;
    public GameState setGameState(String gameId, GameState gs) throws GameNotFoundException;
    public List<GameState> listGames();
}
